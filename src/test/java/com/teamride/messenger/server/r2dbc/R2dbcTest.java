package com.teamride.messenger.server.r2dbc;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.teamride.messenger.server.entity.ChatRoomEntity;
import com.teamride.messenger.server.entity.FriendEntity;
import com.teamride.messenger.server.repository.ChatRoomRepository;
import com.teamride.messenger.server.repository.FriendRepository;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.r2dbc.DataR2dbcTest;

import com.teamride.messenger.server.dto.FriendDTO;
import com.teamride.messenger.server.dto.UserDTO;
import com.teamride.messenger.server.entity.UserEntity;
import com.teamride.messenger.server.repository.UserRepository;

//import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

//@Slf4j
@DataR2dbcTest
public class R2dbcTest {
//
//	@Autowired
//	UserRepository userRepository;
//
//	@Autowired
//	FriendRepository friendRepository;
//
//	@Autowired
//	ChatRoomRepository chatRoomRepository;
//
//	@BeforeAll
//	static void init() {
//		System.out.println("dd");
//	}
//
//	@Test
//	void updateChatRoomImage() {
////		ChatRoomEntity result = chatRoomRepository.findById("f42bc500-f7e0-4c63-8f83-6f43b80c7902").block();
////		result.setRoomImagePath("/uuid-uu-dd");
//		chatRoomRepository.updateRoomImagePathByRoomId("/uuid-uu-dd22", "c3c25c2d-f4b8-4ae0-af0e-c54a00cceaeb")
//				.block();
//
////				.updateRoomImagePathByRoomId("/uuid-uu-dd", "f42bc500-f7e0-4c63-8f83-6f43b80c7902").block();
////		System.out.println(result);
////		System.out.println(result2);
//
////		ChatRoomEntity c = ChatRoomEntity.builder().roomId("test").roomName("testname").build();
////		ChatRoomEntity result3 = chatRoomRepository.save(c).block();
//
////		System.out.println("result3:::"+result3);
//	}
//
//	@Test
//	void userRepoTest() {
//		Stream<UserEntity> userFlux = userRepository.findAll().toStream();
//
////		userFlux.collect(Collectors.toList()).forEach(c -> log.info(c.toString()));
//	}
//
//	@Test
//	void countByEmail() {
//		Mono<Long> l = userRepository.countByEmail("shmin7777@naver.com");
//		assertThat(1L).isEqualTo(l.block());
//	}
//
//	@Test
//	void findByEmail() {
//		Mono<UserEntity> monoUser = userRepository.findByEmail("shmin7777@naver.com");
//		UserEntity user = monoUser.block();
////		log.info("user::"+user);
//	}
//
//	@Test
//	void findByEmailAndPwd() {
//		UserDTO userDTO = UserDTO.builder().email("shmin7777@naver.com").pwd("1234").build();
//		Mono<UserEntity> monoUser = userRepository.findByEmailAndPwd(userDTO.getEmail(), userDTO.getPwd());
//
//		UserEntity entity = monoUser.block();
////		log.info("entity"+entity);
//	}
//
//	@Test
//	void findFriendsByUserid() {
//		int userId = 1;
//
//		userRepository.findFriendsByUserid(userId).toStream().collect(Collectors.toList()).forEach(System.out::println);
//	}
//
//	@Test
//	void findUserBySearchKey() {
//		String searchKey = "최민재";
//		int userId = 1;
//		List<UserDTO> list = userRepository.findUserBySearchKey(searchKey).toStream().filter(el -> el.getId() != userId)
//				.collect(Collectors.toList());
//
//		System.out.println(list.size());
//	}
//
//	@Test
//	void saveFriend() {
//		int userId = 3;
//		int friendId = 1;
//
////		FriendEntity friendEntity = FriendEntity.builder().userId(userId).friendId(friendId).build();
//		FriendDTO friendEntity = FriendDTO.builder().userId(userId).friendId(friendId).build();
//
//		Mono<FriendEntity> mono = null;
//		try {
////			mono = friendRepository.saveFriendEntity(friendEntity);
////			mono.block();
//		} catch (Exception e) {
//
//		}
//		System.out.println(mono.toString());
//	}
}
