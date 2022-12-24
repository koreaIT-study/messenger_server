package com.teamride.messenger.server.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import com.teamride.messenger.server.dto.UserDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table("USER")
public class UserEntity {
	@Id
	private int id; // uuid
	private String email;
	private String name;
	private String pwd;
	private String profilePath; // 나중에
	private String profileImg;

	public UserEntity(UserDTO userDTO) {
		this.id = userDTO.getId();
		this.email = userDTO.getEmail();
		this.name = userDTO.getName();
		this.pwd = userDTO.getPwd();
		this.profilePath = userDTO.getProfilePath();
		this.profileImg = userDTO.getProfileImg();
	}
	
	public UserDTO toUserDTO(UserEntity userEntity) {
		return UserDTO.builder()
				.id(userEntity.id)
				.email(userEntity.email)
				.name(userEntity.name)
				.profileImg(userEntity.profileImg)
				.profilePath(userEntity.profilePath)
				.profileOriginalImg("")
				.build();
	}
}
