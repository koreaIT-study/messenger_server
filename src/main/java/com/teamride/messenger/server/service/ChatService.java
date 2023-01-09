package com.teamride.messenger.server.service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

import com.fasterxml.jackson.databind.util.BeanUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.web.multipart.MultipartFile;

import com.teamride.messenger.server.config.KafkaConstants;
import com.teamride.messenger.server.dto.ChatMessageDTO;
import com.teamride.messenger.server.dto.ChatRoomDTO;
import com.teamride.messenger.server.dto.UserDTO;
import com.teamride.messenger.server.entity.ChatMessageEntity;
import com.teamride.messenger.server.entity.ChatRoomEntity;
import com.teamride.messenger.server.entity.UserEntity;
import com.teamride.messenger.server.repository.ChatMessageRepository;
import com.teamride.messenger.server.repository.ChatRoomRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@Service
@RequiredArgsConstructor
public class ChatService {
	private final ChatMessageRepository chatMessageRepo;
	private final ChatRoomRepository chatRoomRepository;

	@Autowired
	private KafkaTemplate<String, ChatMessageDTO> kafkaTemplate;

	public Flux<ChatMessageDTO> getAllMessageWithRoomId(String roomId, String time) {
		if ("".equals(time)) {
			return chatMessageRepo.getAllMessage(roomId);
		}
		ChatMessageDTO chatMessageDTO = ChatMessageDTO.builder().roomId(roomId).timestamp(time).build();
		return chatMessageRepo.getBeforeMessage(chatMessageDTO);
	}

	public Flux<ChatRoomDTO> getAllRoomWithUserId(String userId) {
		return chatRoomRepository.getAllRoom(userId);
	}

	public Mono<ChatRoomDTO> getRoom(String roomId) {
		return chatRoomRepository.getRoom(roomId);
	}

	public Mono<ChatRoomDTO> findRoomById(String roomId) {
		List<String> members = new ArrayList<>();
		Mono<ChatRoomDTO> room = chatRoomRepository.getRoomWithUsers(roomId).doOnSuccess(r -> r.setUserId(members));
		chatRoomRepository.getRoomMember(roomId).subscribe(members::add);
		room.subscribe();

		return room;
	}

//	@Transactional
	public ChatRoomDTO mkRoom(ChatRoomDTO room) {
		// uuid 만들고
		// insert
		ChatRoomDTO room2 = null;

		if (room.getIsGroup().equals("N")) {
			HashMap<String, String> map = new HashMap<>();
			List<String> userId = room.getUserId();
//			map.put("friendId", userId.get(0));
//			map.put("myId", userId.get(1));
			room2 = chatRoomRepository.getOneByOneRoom(userId.get(1), userId.get(0)).block();

			if (room2 != null)
				return room2;
		}

		ChatRoomDTO newRoom;
		while (true) {
			try {
				newRoom = ChatRoomDTO.create(room);
				ChatRoomEntity chatRoomEntity = ChatRoomEntity.builder().roomId(newRoom.getRoomId())
						.roomName(newRoom.getRoomName()).isGroup(newRoom.getIsGroup()).build();

				chatRoomRepository.save(chatRoomEntity).block();

				List<String> memberList = room.getUserId();
				for (int i = 0; i < memberList.size(); i++) {
					chatRoomRepository.insertRoomMember(newRoom.getRoomId(), memberList.get(i)).block();

				}
				break;
			} catch (Exception e) {
				log.error("make room error :::: {}", e);
				throw new RuntimeException("Another Exception {}", e);
			}
		}

		return newRoom;
	}

	@Transactional
	public Mono<ChatMessageEntity> insertMessage(ChatMessageDTO message) {
		ChatMessageEntity chatMessage = new ChatMessageEntity(message);
		return chatMessageRepo.save(chatMessage);
	}

	public int changeRoomImg(MultipartFile multipartFile, String roomId) {
		try {
			// file 저장
			if (multipartFile != null) {
				String realPath = KafkaConstants.PROFILE_LOCATION;

				final String originalFilename = multipartFile.getOriginalFilename();
				final UUID uuid = UUID.randomUUID();
				String realFilename = uuid + "." + originalFilename.substring(originalFilename.lastIndexOf(".") + 1);

				File dest = new File(realPath + "/" + realFilename);
				File dir = new File(realPath);
				if (!dir.exists()) {
					dir.mkdir();
				}

				if (!dest.exists())
					multipartFile.transferTo(dest);
				chatRoomRepository.updateRoomImagePathByRoomId(realFilename, roomId).block();
			}
			// save user
			return 1;
		} catch (Exception e) {
			log.error("sigh up error", e);
			return 0;
		}
	}


	@Transactional
	public Mono<Integer> fileSend(List<MultipartFile> files, ChatMessageDTO msg){
		log.info("in fileSend msg ::: {}", msg);
		// 파일을 먼저 다 저장하고
		StringBuilder sb = new StringBuilder();
		String realPath = sb.append(KafkaConstants.MSG_FILE_LOCATION)
							.append('/')
							.append(msg.getRoomId())
							.toString();

		List<ChatMessageDTO> list = new ArrayList<>();

		try {
			for(MultipartFile file : files){
				ChatMessageDTO dto = new ChatMessageDTO();
				BeanUtils.copyProperties(msg, dto);

				String uuid = UUID.randomUUID().toString();
				String fileName = uuid + "||"+ file.getOriginalFilename();
				dto.setExtension(fileName.substring(fileName.lastIndexOf(".")));

				File msgFileRoot = new File(KafkaConstants.MSG_FILE_LOCATION);
				File dir = new File(realPath);
				File dest = new File(realPath + '/' + fileName);

				if(!msgFileRoot.exists()) msgFileRoot.mkdir();
				if(!dir.exists()) dir.mkdir();
				if(!dest.exists()) file.transferTo(dest);

				dto.setMessage(fileName);
				list.add(dto);
			}
		} catch (IOException e) {
			return Mono.empty();
		}

		// 그다음 비동기로 메시지 전송
//		CompletableFuture.runAsync(() ->{
			for(ChatMessageDTO dto : list){
				dto.setTimestamp();
				Mono<ChatMessageEntity> monoChatMessage = insertMessage(dto);
				monoChatMessage.subscribe(s -> log.info("msg file save :::"));

				String partitionKey = dto.getRoomId().substring(0, 2);
				ListenableFuture<SendResult<String, ChatMessageDTO>> future = kafkaTemplate.send(KafkaConstants.CHAT_CLIENT,
						partitionKey, dto);

				future.addCallback((result) -> {
					log.info("message 전송 성공, message :: {}, result is :: {}", dto, result);
				}, (ex) -> {
					log.error("message 전송 실패, message :: {}, error is :: {}", dto, ex);
				});
			}
//		});
		return Mono.just(list.size());
	}
}
