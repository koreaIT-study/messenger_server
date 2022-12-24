package com.teamride.messenger.server.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
	private int id; // uuid
	private String email;
	private String name;
	private String pwd;
	private String profilePath; // 나중에
	private String profileImg;
	private String profileOriginalImg;
}
