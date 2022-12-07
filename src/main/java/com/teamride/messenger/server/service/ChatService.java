package com.teamride.messenger.server.service;

import java.sql.SQLException;
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
	
	public ChatRoomDTO findRoomById(String roomId) {
		ChatRoomDTO room = chatMapper.findRoomById(roomId);
		room.setUserId(chatMapper.getRoomMember(roomId));
		return room;
	}

	@Transactional
	public ChatRoomDTO mkRoom(ChatRoomDTO room) {
		// uuid 만들고
		// insert
		ChatRoomDTO room2 = null;
		int cnt = 0;
		while(true){
			try {
				room2 = ChatRoomDTO.create(room);
				chatMapper.insertRoom(room2);
				chatMapper.insertRoomMember(room2);
				break;
			} catch(SQLException slqe){
				log.error("UUID Duplicate :::::::");
				if(cnt == 3) throw new RuntimeException("UUID Duplicate or {}", slqe);
				cnt++;
			} catch (Exception e) {
				log.error("make room error :::: {}", e);
				throw new RuntimeException("Another Exception {}", e);
			}
		}

		return room2;
	}

	@Transactional
	public void insertMessage(ChatMessageDTO message){
		try {
			chatMapper.insertMessage(message);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
