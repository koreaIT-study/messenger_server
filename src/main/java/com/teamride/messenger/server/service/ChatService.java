package com.teamride.messenger.server.service;

import java.util.List;

import com.teamride.messenger.server.dto.ChatRoomDTO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

	@Transactional(rollbackFor = RuntimeException.class)
	public ChatRoomDTO mkRoom(ChatRoomDTO room) {
		// uuid 만들고
		// insert
		int makeCnt = 0;
		ChatRoomDTO room2 = null;

		while (makeCnt < 3) {
			try {
				try {
					room2 = ChatRoomDTO.create(room);
					chatMapper.insertRoom(room2);
				} catch (Exception e) {
					makeCnt++;
					log.error("UUID Duplicate then retry\n", e.getLocalizedMessage());
				}

				for (String id : room.getUserId()) {

				}
			} catch (Exception e) {
				throw new RuntimeException("make room error", e);
			}

		}

		return room2;
	}

}
