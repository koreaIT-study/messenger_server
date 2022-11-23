package com.teamride.messenger.server.service;

import java.util.List;

import org.apache.ibatis.javassist.NotFoundException;
import org.springframework.stereotype.Service;

import com.teamride.messenger.server.dto.FriendInfoDTO;
import com.teamride.messenger.server.dto.UserDTO;
import com.teamride.messenger.server.mapper.UserMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {
    private final UserMapper userMapper;

    public UserDTO checkAndInsertUser(UserDTO userDTO) {
        if (userMapper.checkExistUser(userDTO.getEmail()) == 0) {
            // 처음 회원가입
        	userMapper.saveUser(userDTO);
            log.info(userDTO.getName() + " 회원가입 완료");
        }
        log.info(userDTO.getName() + " 가입된 유저");
        return userMapper.getUserInfoWithSocial(userDTO);
    }

    public UserDTO getUserInfo(UserDTO userDTO) throws NotFoundException {
        final UserDTO userInfo = userMapper.getUserInfo(userDTO);
        if (userInfo == null) {
            throw new NotFoundException("not found user");
        }
        return userInfo;
    }

    public int saveUser(UserDTO userDTO) throws Exception {
        userDTO.getProfilePath();

        final int result = userMapper.saveUser(userDTO);
        if (result == 0) {
            throw new Exception("not saved user");
        }
        return result;
    }

    public List<FriendInfoDTO> getFriendList(int userId) throws NotFoundException {
        List<FriendInfoDTO> result = userMapper.getFriendList(userId);
        if (result.isEmpty()) {
            throw new NotFoundException("not found friends");
        }
        return result;
    }
}
