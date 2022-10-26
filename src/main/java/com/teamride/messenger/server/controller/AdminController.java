package com.teamride.messenger.server.controller;

import javax.servlet.http.HttpServletRequest;

import org.apache.ibatis.javassist.NotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.teamride.messenger.server.dto.AdminDTO;
import com.teamride.messenger.server.service.AdminService;
import com.teamride.messenger.server.util.RestResponse;

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

	@PostMapping("/loginAction")
	public RestResponse loginAction(HttpServletRequest req, @RequestBody AdminDTO adminDTO) {
		try {
			AdminDTO userInfo = adminService.getUserInfo(adminDTO);
			return new RestResponse(userInfo);
		} catch (NotFoundException e) {
			return new RestResponse(1, e.getLocalizedMessage(), null);
		}
	}

}
