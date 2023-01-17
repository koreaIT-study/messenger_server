package com.teamride.messenger.server.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.util.List;

import org.apache.commons.fileupload.disk.DiskFileItem;
import org.apache.commons.io.IOUtils;
import org.springframework.http.HttpEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.teamride.messenger.server.config.KafkaConstants;
import com.teamride.messenger.server.dto.ChatMessageDTO;
import com.teamride.messenger.server.dto.ChatRoomDTO;
import com.teamride.messenger.server.service.ChatService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@RestController
@RequiredArgsConstructor
public class ChatController {

	private final ChatService chatService;

	@GetMapping("/get-chat-message")
	public Flux<ChatMessageDTO> getChatMessage(String roomId, String time) {

		return chatService.getAllMessageWithRoomId(roomId, time);
	}

	@PostMapping("/room")
	public ChatRoomDTO mkRoom(@RequestBody ChatRoomDTO room) {
		return chatService.mkRoom(room);
	}

	@PostMapping("/get-room")
	public Mono<ChatRoomDTO> getRoom(@RequestBody String roomId) {
		return chatService.getRoom(roomId);
	}

	@PostMapping("/find-room-by-id")
	public Mono<ChatRoomDTO> findRoomById(@RequestBody String roomId) {
		return chatService.findRoomById(roomId);
	}

	@GetMapping("/get-room-list")
	public Flux<ChatRoomDTO> getRoomList(String userId) {
		return chatService.getAllRoomWithUserId(userId);
	}

	@PostMapping("/change-room-img")
	public String changeRoomImg(@RequestPart(required = false, value = "file") MultipartFile multipartFile,
			String roomId) {
		return chatService.changeRoomImg(multipartFile, roomId);
	}

	@PostMapping("/messege-file")
	public Mono<Integer> fileSend(@RequestPart(required = false, value = "files") List<MultipartFile> files,
			@RequestPart(value = "msg") ChatMessageDTO msg) {
		return chatService.fileSend(files, msg);
	}

	@GetMapping("/downFile/{roomId}/{msg}")
	public MultiValueMap<String, MultipartFile> fileDownload(@PathVariable String roomId, @PathVariable String msg) {
		MultiValueMap<String, MultipartFile> map = new LinkedMultiValueMap<>();
		File file = new File(KafkaConstants.MSG_FILE_LOCATION + '/' + roomId + '/' + msg);
		try {
			DiskFileItem fileItem = new DiskFileItem(msg.substring(msg.lastIndexOf("||"))
													, Files.probeContentType(file.toPath())
													, false
													, file.getName()
													, (int) file.length()
													, file.getParentFile());
			FileInputStream input = new FileInputStream(file);
			OutputStream os = fileItem.getOutputStream();
			IOUtils.copy(input,os);

			MultipartFile multipartFile = new CommonsMultipartFile(fileItem);
			map.add("file", multipartFile);
			return map;
		} catch (IOException e) {
			return map;
		}
//		MultipartBodyBuilder builder = new MultipartBodyBuilder();
//		builder.part("file", new FileSystemResource(file));
//		return builder.build();
	}
}
