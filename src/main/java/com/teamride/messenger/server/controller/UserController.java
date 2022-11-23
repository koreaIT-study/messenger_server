package com.teamride.messenger.server.controller;

import java.util.List;

import javax.mail.MessagingException;

import org.apache.ibatis.javassist.NotFoundException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.teamride.messenger.server.dto.FriendInfoDTO;
import com.teamride.messenger.server.dto.UserDTO;
import com.teamride.messenger.server.service.UserService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@RequiredArgsConstructor
public class UserController {
    private final UserService adminService;
    private final MailService mailService;

    @PostMapping("/social_login")
    public void socialLogin(@RequestBody UserDTO userDTO) {
        log.info("server 들어옴");
        adminService.checkAndInsertUser(userDTO);
    }

    @PostMapping("/loginAction")
    public UserDTO loginAction(@RequestBody UserDTO userDTO) {
        try {
            return adminService.getUserInfo(userDTO);
        } catch (NotFoundException e) {
            return null;
        }
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
    public Integer signUp(@RequestBody UserDTO userDTO) {
        try {
            return adminService.saveUser(userDTO);
        } catch (Exception e) {
            return 0;
        }
    }

    @GetMapping("/getFriends")
    public List<FriendInfoDTO> getFriends(int userId) throws NotFoundException {
        return adminService.getFriendList(userId);
    }

}
