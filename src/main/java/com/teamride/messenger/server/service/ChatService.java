package com.teamride.messenger.server.service;

import java.util.List;

import com.teamride.messenger.server.dto.ChatRoomDTO;
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

	public ChatRoomDTO mkRoom(ChatRoomDTO room){
		// uuid 만들고
		// insert
		boolean flag = true;
		ChatRoomDTO room2 = null;
		while(flag){
			try {
				room2 = ChatRoomDTO.create(room);
				chatMapper.insertRoom(room2);

				for(String id : room.getUserId()){

				}

				flag = false;
			} catch (Exception e) {
				log.error("UUID Duplicate then retry\n", e.getLocalizedMessage());
			}
		}

		return room2;
	}

}
