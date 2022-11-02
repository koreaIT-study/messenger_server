package com.teamride.messenger.server.controller;

import javax.mail.MessagingException;

import org.apache.ibatis.javassist.NotFoundException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
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
    private final MailService mailService;

    @PostMapping("/social_login")
    public void socialLogin(@RequestBody AdminDTO adminDTO) {
        log.info("server 들어옴");
        adminService.checkAndInsertUser(adminDTO);
    }

    @PostMapping("/loginAction")
    public RestResponse loginAction(@RequestBody AdminDTO adminDTO) {
        try {
            final AdminDTO userInfo = adminService.getUserInfo(adminDTO);
            return new RestResponse(userInfo);
        } catch (NotFoundException e) {
            return new RestResponse(1, e.getLocalizedMessage(), null);
        }
    }

    @GetMapping("/smtpRequest")
    public RestResponse smtp(@RequestParam String email) {
        try {
            return new RestResponse(mailService.joinEmail(email));
        } catch (MessagingException e) {
            log.error("mail send error", e.getLocalizedMessage());
            return new RestResponse(1, e.getLocalizedMessage(), null);
        }
    }
}
