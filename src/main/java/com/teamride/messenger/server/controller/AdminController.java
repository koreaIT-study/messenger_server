package com.teamride.messenger.server.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.teamride.messenger.server.dto.AdminDTO;
import com.teamride.messenger.server.service.AdminService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@RequiredArgsConstructor
public class AdminController {
	private final AdminService adminService;

	@PostMapping("/social_login")
	public void socialLogin(@RequestBody AdminDTO adminDTO) {
		log.info("server 들어옴");
		adminService.checkAndInsertUser(adminDTO);
	}
}
