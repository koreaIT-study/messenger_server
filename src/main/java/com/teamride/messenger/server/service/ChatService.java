package com.teamride.messenger.server.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.teamride.messenger.server.dto.ChatMessageDTO;
import com.teamride.messenger.server.dto.ChatRoomDTO;
import com.teamride.messenger.server.entity.ChatMessage;
import com.teamride.messenger.server.entity.ChatRoomEntity;
import com.teamride.messenger.server.repository.ChatMessageRepo;
import com.teamride.messenger.server.repository.ChatRoomRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@Service
@RequiredArgsConstructor
public class ChatService {
	private final ChatMessageRepo chatMessageRepo;
	private final ChatRoomRepository chatRoomRepository;

	public Flux<ChatMessageDTO> getAllMessageWithRoomId(String roomId) {
		return chatMessageRepo.getAllMessage(roomId);
	}

	public Flux<ChatRoomDTO> getAllRoomWithUserId(String userId) {
		return chatRoomRepository.getAllRoom(userId);
	}

	public Mono<ChatRoomDTO> getRoom(String roomId) {
		return chatRoomRepository.getRoom(roomId);
	}

	public Mono<ChatRoomDTO> findRoomById(String roomId) {
		List<String> members = new ArrayList<>();
		Mono<ChatRoomDTO> room = chatRoomRepository.getRoomWithUsers(roomId).doOnSuccess(r->r.setUserId(members));
		chatRoomRepository.getRoomMember(roomId).subscribe(members::add);
		room.subscribe();
		
	
		return room;
	}

	@Transactional(value = "transactionManager")
	public Mono<ChatRoomDTO> mkRoom(ChatRoomDTO room) {
		// uuid 만들고
		// insert
		Mono<ChatRoomDTO> room2 = null;

		if (room.getIsGroup().equals("N")) {
			HashMap<String, String> map = new HashMap<>();
			List<String> userId = room.getUserId();
			map.put("friendId", userId.get(0));
			map.put("myId", userId.get(1));
			room2 = chatRoomRepository.getOneByOneRoom(map);
			if (room2 != null)
				return room2;
		}

		ChatRoomDTO newRoom;
		while (true) {
			try {
				newRoom = ChatRoomDTO.create(room);
				ChatRoomEntity chatRoomEntity = ChatRoomEntity.builder()
						.roomId(newRoom.getRoomId())
						.roomName(newRoom.getRoomName())
						.isGroup(newRoom.getIsGroup())
						.build();

				chatRoomRepository.save(chatRoomEntity).subscribe();

				List<String> memberList = room.getUserId();
				for (int i = 0; i < memberList.size(); i++) {
					chatRoomRepository.insertRoomMember(newRoom.getRoomId(), memberList.get(i)).subscribe();

				}
				break;
			} catch (Exception e) {
				log.error("make room error :::: {}", e);
				throw new RuntimeException("Another Exception {}", e);
			}
		}

		return Mono.just(newRoom);
	}

	@Transactional(value = "transactionManager")
	public Mono<ChatMessage> insertMessage(ChatMessageDTO message) {
		message.setTimestamp();
		ChatMessage chatMessage = new ChatMessage(message);
		return chatMessageRepo.save(chatMessage);
	}

}
