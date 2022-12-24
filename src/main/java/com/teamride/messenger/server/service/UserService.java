package com.teamride.messenger.server.service;

import com.teamride.messenger.server.entity.FriendEntity;
import com.teamride.messenger.server.repository.FriendRepository;
import org.apache.ibatis.javassist.NotFoundException;
import org.springframework.stereotype.Service;

import com.teamride.messenger.server.dto.FriendDTO;
import com.teamride.messenger.server.dto.FriendInfoDTO;
import com.teamride.messenger.server.dto.UserDTO;
import com.teamride.messenger.server.entity.UserEntity;
import com.teamride.messenger.server.repository.UserRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {
	private final UserRepository userRepository;
	private final FriendRepository friendRepository;

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
			UserEntity entity = new UserEntity(dto);
			userRepository.save(entity).subscribe();
			return 1;
		} catch (Exception e) {
			log.error("sigh up error", e);
			return 0;
		}
	}

	public Flux<FriendInfoDTO> getFriendList(int userId) throws NotFoundException {
		return userRepository.findFriendsByUserid(userId);
	}

	public Flux<UserDTO> searchUser(String searchKey, int userId) {
		return userRepository.findUserBySearchKey(searchKey).filter(el -> el.getId() != userId);
	}

	public Mono<FriendEntity> addFriend(FriendDTO dto) {
		return friendRepository.saveFriendEntity(dto);
	}
}
