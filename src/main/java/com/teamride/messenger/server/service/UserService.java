package com.teamride.messenger.server.service;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.teamride.messenger.server.config.KafkaConstants;
import com.teamride.messenger.server.dto.FriendDTO;
import com.teamride.messenger.server.dto.FriendInfoDTO;
import com.teamride.messenger.server.dto.SaveUserDTO;
import com.teamride.messenger.server.dto.UserDTO;
import com.teamride.messenger.server.entity.FriendEntity;
import com.teamride.messenger.server.entity.UserEntity;
import com.teamride.messenger.server.repository.FriendRepository;
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
		return userRepository.findByEmail(userEntity.getEmail()).map(entity -> entity.toUserDTO(entity));
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

	public int saveUser(MultipartFile multipartFile, UserDTO saveUserDTO) throws Exception {
		try {
			String profilePath = "";
			String profileImage = ""; // profile name

			// file 저장
			if (multipartFile != null) {
				String realPath = KafkaConstants.PROFILE_LOCATION;

				final String originalFilename = multipartFile.getOriginalFilename();
				final UUID uuid = UUID.randomUUID();
				String realFilename = uuid + "." + originalFilename.substring(originalFilename.lastIndexOf(".") + 1);

				File dest = new File(realPath + "/" + realFilename);
				File dir = new File(realPath);
				if (!dir.exists()) {
					dir.mkdir();
				}
				
				if (!dest.exists())
					multipartFile.transferTo(dest);

				profilePath = realPath + "/" + realFilename;
				profileImage = originalFilename;
				saveUserDTO.setProfileImg(realFilename);
				saveUserDTO.setProfilePath(profilePath);

			}

			// save user
			UserEntity entity = new UserEntity(saveUserDTO);
			userRepository.save(entity).block();
			return 1;
		} catch (Exception e) {
			log.error("sigh up error", e);
			return 0;
		}
	}

	public Flux<FriendInfoDTO> getFriendList(int userId) {
		return userRepository.findFriendsByUserid(userId);
	}

	public Flux<UserDTO> searchUser(String searchKey, int userId) {
		return userRepository.findUserBySearchKey(searchKey).filter(el -> el.getId() != userId);
	}

	public Mono<FriendEntity> addFriend(FriendDTO dto) {
//		return friendRepository.saveFriendEntity(dto);
		return friendRepository.saveFriendEntity(dto.getUserId(), dto.getFriendId());
	}
}
