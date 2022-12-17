package com.teamride.messenger.server.dto;

import java.util.concurrent.atomic.AtomicLong;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ChatMessageDTO implements Comparable<ChatMessageDTO> {
	private AtomicLong messageId = new AtomicLong(); // pk

	private String roomId;
	private String writer;
	private String message;
	private String timestamp;
	private String writerName;

	@Override
	public int compareTo(ChatMessageDTO chatMessageDTO) {
		if (this.messageId.get() > chatMessageDTO.messageId.get())
			return 1;
		else if (this.messageId.get() < chatMessageDTO.messageId.get())
			return -1;
		return 0;
	}
}