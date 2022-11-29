package com.teamride.messenger.server.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.teamride.messenger.server.dto.UserDTO;
import com.teamride.messenger.server.dto.FriendInfoDTO;

@Mapper
public interface UserMapper {
	// 전체 회원 보기
	public List<UserDTO> getAll();
	
	// 회원 가입된 유저인지 체크
	// 0 : 회원가입 안되어있음, 1: 가입된 유저
	public int checkExistUser(@Param("email") String email);
	
	// 회원 가입 
	public int saveUser(UserDTO adminDTO);

	// 회원 정보 가져오기
	public UserDTO getUserInfo(UserDTO adminDTO);
	
	public UserDTO getUserInfoWithSocial(UserDTO adminDTO);
	
	// 친구 정보 가져오기
	public List<FriendInfoDTO> getFriendList(int userId);
	
}
