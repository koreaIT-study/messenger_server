package com.teamride.messenger.server.service;

import org.apache.ibatis.javassist.NotFoundException;
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

    public AdminDTO getUserInfo(AdminDTO adminDTO) throws NotFoundException {
        final AdminDTO userInfo = adminMapper.getUserInfo(adminDTO);
        if (userInfo == null) {
            throw new NotFoundException("not found user");
        }
        return userInfo;
    }

    public int saveUser(AdminDTO adminDTO) throws Exception {
        final int result = adminMapper.saveUser(adminDTO);
        if (result == 0) {
            throw new Exception("not saved user");
        }
        return result;
    }
}
