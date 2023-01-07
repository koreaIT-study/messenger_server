package com.teamride.messenger.server.entity;

import org.springframework.data.relational.core.mapping.Table;

import com.teamride.messenger.server.dto.ChatMessageDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "C_MESSAGE")
public class ChatMessageEntity {
	private String roomId;
	private String writer;
	private String message;
	private String timestamp;
	private String extension;

	public ChatMessageEntity(ChatMessageDTO chatMessageDTO) {
		this.roomId = chatMessageDTO.getRoomId();
		this.writer = chatMessageDTO.getWriter();
		this.message = chatMessageDTO.getMessage();
		this.timestamp = chatMessageDTO.getTimestamp();
		this.extension = chatMessageDTO.getExtension();
	}
}
