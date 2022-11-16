package com.teamride.messenger.server.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.teamride.messenger.server.dto.ChatMessageDTO;
import com.teamride.messenger.server.mapper.ChatMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class ChatService {
	private final ChatMapper chatMapper;

	public List<ChatMessageDTO> getAllMessageWithRoomId(String roomId) {
		return chatMapper.getAllMessageWithRoomId(roomId);
	}

}
