package com.teamride.messenger.server.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdminDTO {
	private int id;
	private String email;
	private String name;
	private String pwd;
	private String profileImg;
}
