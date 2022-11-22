package com.teamride.messenger.server.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FriendInfoDTO {
	private int id; // friend id
	private String email;
	private String name;
	private String profileImg;
	private String roomId;
}
