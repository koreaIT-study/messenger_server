package com.teamride.messenger.server.service;

import org.springframework.stereotype.Service;

import com.teamride.messenger.server.dto.AdminDTO;
import com.teamride.messenger.server.mapper.AdminMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class AdminService {
	private final AdminMapper adminMapper;

	public void checkAndInsertUser(AdminDTO adminDTO) {
		if (adminMapper.checkExistUser(adminDTO.getEmail()) == 0) {
			// 처음 회원가입
			adminMapper.saveUser(adminDTO);
			log.info(adminDTO.getName() + " 회원가입 완료");
			return;
		}
		log.info(adminDTO.getName() + " 가입된 유저");
	}
}
