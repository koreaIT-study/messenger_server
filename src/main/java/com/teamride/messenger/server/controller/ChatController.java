package com.teamride.messenger.server.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

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
	public Flux<ChatMessageDTO> getChatMessage(String roomId) {
		return chatService.getAllMessageWithRoomId(roomId);
	}

	@PostMapping("/room")
	public Mono<ChatRoomDTO> mkRoom(@RequestBody ChatRoomDTO room) {
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
}
