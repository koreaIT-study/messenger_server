package com.teamride.messenger.server.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.teamride.messenger.server.dto.ChatMessageDTO;
import com.teamride.messenger.server.dto.ChatRoomDTO;
import com.teamride.messenger.server.service.ChatService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
public class ChatController {

	private final ChatService chatService;

	@GetMapping("/get-chat-message")
	public List<ChatMessageDTO> getChatMessage(String roomId) {
		List<ChatMessageDTO> result = chatService.getAllMessageWithRoomId(roomId);
		log.info("get chat message api");
		log.info("roomId is {}, result :: {} ", roomId, result);
		return result;
	}

	@PostMapping("/room")
	public ChatRoomDTO mkRoom(@RequestBody ChatRoomDTO room){
		return chatService.mkRoom(room);
	}
}
