package com.teamride.messenger.server.service;

import java.util.List;
import java.util.stream.Collectors;

import org.apache.ibatis.javassist.NotFoundException;
import org.springframework.stereotype.Service;

import com.teamride.messenger.server.dto.FriendDTO;
import com.teamride.messenger.server.dto.FriendInfoDTO;
import com.teamride.messenger.server.dto.UserDTO;
import com.teamride.messenger.server.entity.UserEntity;
import com.teamride.messenger.server.mapper.UserMapper;
import com.teamride.messenger.server.repository.UserRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {
	private final UserMapper userMapper;
	private final UserRepository userRepository;

	public Mono<UserDTO> checkAndInsertUser(UserDTO userDTO) {
		UserEntity userEntity = new UserEntity(userDTO);
		Mono<Long> countMono = userRepository.countByEmail(userEntity.getEmail()).doOnSuccess(cnt -> {
			if (cnt == 0) {
				userRepository.save(userEntity).subscribe(c -> log.info(c.getName() + "회원가입 완료"));
			}
		});
		countMono.block();
		return userRepository.findByEmail(userEntity.getEmail()).map(entity-> entity.toUserDTO(entity));
	}

	public Mono<UserDTO> getUserInfo(UserDTO userDTO) {
//		final UserDTO userInfo = userMapper.getUserInfo(userDTO);
//		if (userInfo == null) {
//			throw new NotFoundException("not found user");
//		}
//		return userInfo;
		return userRepository.findByEmailAndPwd(userDTO.getEmail(), userDTO.getPwd())
				.map(entity -> entity.toUserDTO(entity));

	}

	public int saveUser(UserDTO dto) throws Exception {
		try {
			return userMapper.saveUser(dto);
		} catch (Exception e) {
			log.error("sigh up error", e);
			return 0;
		}
	}

	public List<FriendInfoDTO> getFriendList(int userId) throws NotFoundException {
		List<FriendInfoDTO> result = userMapper.getFriendList(userId);
		// if (result.isEmpty()) {
		// throw new NotFoundException("not found friends");
		// }
		return result;
	}

	public List<UserDTO> searchUser(String searchKey, int userId) {
		final List<UserDTO> searchList = userMapper.searchUser(searchKey);
		return searchList.stream().filter(v -> v.getId() != userId).collect(Collectors.toList());
	}

	public Integer addFriend(FriendDTO dto) {
		return userMapper.addFriend(dto);
	}
}
