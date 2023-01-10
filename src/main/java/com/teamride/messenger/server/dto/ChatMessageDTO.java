package com.teamride.messenger.server.dto;

import java.text.SimpleDateFormat;
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
	private String extension;
	private String profileImg;

	public void setTimestamp(){
		this.timestamp = new SimpleDateFormat("YYYY-MM-dd HH:mm:ss.SSS").format(System.currentTimeMillis());
	}

}