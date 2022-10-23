package com.teamride.messenger.server.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.teamride.messenger.server.dto.AdminDTO;

@Mapper
public interface AdminMapper {
	
	// 회원가입
	public void save(AdminDTO adminDTO);
	
	// 전체 회원 보기
	public List<AdminDTO> selectAll();
}
