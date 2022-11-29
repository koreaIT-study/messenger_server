package com.teamride.messenger.server.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.teamride.messenger.server.dto.ChatMessageDTO;
import com.teamride.messenger.server.dto.ChatRoomDTO;
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
	public List<ChatRoomDTO> getAllRoomWithUserId(String userId) {
		return chatMapper.getAllRoomWithUserId(userId);
	}

	@Transactional(rollbackFor = RuntimeException.class)
	public ChatRoomDTO mkRoom(ChatRoomDTO room) {
		// uuid 만들고
		// insert
		ChatRoomDTO room2 = null;

        try {
            room2 = ChatRoomDTO.create(room);
            chatMapper.insertRoom(room2);
            chatMapper.insertRoomMember(room2);
        } catch (Exception e) {
            throw new RuntimeException("UUID Duplicate");
        }

		return room2;
	}

}
