package com.teamride.messenger.server.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.teamride.messenger.server.dto.AdminDTO;

@Mapper
public interface AdminMapper {
	// 전체 회원 보기
	public List<AdminDTO> getAll();
	
	// 회원 가입된 유저인지 체크
	// 0 : 회원가입 안되어있음, 1: 가입된 유저
	public int checkExistUser(@Param("email") String email);
	
	// 회원 가입 
	public int saveUser(AdminDTO adminDTO);
	
}
