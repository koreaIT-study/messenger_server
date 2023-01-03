package com.teamride.messenger.server.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Table("C_ROOM")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChatRoomEntity {
	private String roomId;
	private String roomName;
	private String isGroup;
	private String roomImagePath;
}
