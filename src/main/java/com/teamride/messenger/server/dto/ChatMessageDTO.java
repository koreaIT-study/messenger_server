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
public class ChatMessageDTO{
	private String roomId;
	private String writer;
	private String message;
	private String timestamp;
	private String writerName;
}