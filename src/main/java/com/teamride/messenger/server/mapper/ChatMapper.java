package com.teamride.messenger.server.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.teamride.messenger.server.dto.ChatMessageDTO;
import com.teamride.messenger.server.dto.ChatRoomDTO;

@Mapper
public interface ChatMapper {
	// 해당 roomId의 message 전체 조회
	public List<ChatMessageDTO> getAllMessageWithRoomId(String m_room_id);


	public void insertRoom(ChatRoomDTO chatRoom);
}
