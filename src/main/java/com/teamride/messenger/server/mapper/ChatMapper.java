package com.teamride.messenger.server.mapper;

import java.sql.SQLException;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.teamride.messenger.server.dto.ChatMessageDTO;
import com.teamride.messenger.server.dto.ChatRoomDTO;

@Mapper
public interface ChatMapper {
	// 해당 roomId의 message 전체 조회
	public List<ChatMessageDTO> getAllMessageWithRoomId(String m_room_id);

	// 해당 userId의 room 전체 조회
	public List<ChatRoomDTO> getAllRoomWithUserId(String userId);

	public ChatRoomDTO findRoomById(String roomId);
	
	public void insertRoom(ChatRoomDTO chatRoom) throws SQLException;

	public void insertRoomMember(ChatRoomDTO chatRoom) throws SQLException;

	public void insertMessage(ChatMessageDTO message) throws SQLException;
}
