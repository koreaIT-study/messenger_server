package com.teamride.messenger.server.controller;

import javax.mail.MessagingException;
import com.teamride.messenger.server.entity.FriendEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.teamride.messenger.server.dto.FriendDTO;
import com.teamride.messenger.server.dto.FriendInfoDTO;
import com.teamride.messenger.server.dto.SaveUserDTO;
import com.teamride.messenger.server.dto.UserDTO;
import com.teamride.messenger.server.service.UserService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@Slf4j
@RequiredArgsConstructor
public class UserController {
	private final UserService adminService;
	private final MailService mailService;

	@PostMapping("/social_login")
	public Mono<UserDTO> socialLogin(@RequestBody UserDTO userDTO) {
		log.info("server 들어옴");
		return adminService.checkAndInsertUser(userDTO);
	}

	@PostMapping("/loginAction")
	public Mono<UserDTO> loginAction(@RequestBody UserDTO userDTO) {
		return adminService.getUserInfo(userDTO);
	}

	@GetMapping("/smtpRequest")
	public String smtp(@RequestParam String email) {
		try {
			return mailService.joinEmail(email);
		} catch (MessagingException e) {
			return null;
		}
	}

	@PostMapping("/signUp")
	public Integer signUp(@RequestPart(required = false, value = "file") MultipartFile multipartFile, UserDTO saveUserDTO) {
		try {
			return adminService.saveUser(multipartFile, saveUserDTO);
		} catch (Exception e) {
			return 0;
		}
	}

	@GetMapping("/getFriends")
	public Flux<FriendInfoDTO> getFriends(int userId) {
		return adminService.getFriendList(userId);
	}

	@GetMapping("/searchUser")
	public Flux<UserDTO> searchUser(@RequestParam String searchKey, @RequestParam int userId) {
		return adminService.searchUser(searchKey, userId);
	}

	@PostMapping("/addFriend")
	public Mono<FriendEntity> addFriend(@RequestBody FriendDTO dto) {
		return adminService.addFriend(dto);
	}
}
