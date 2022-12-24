package com.teamride.messenger.server.r2dbc;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.r2dbc.DataR2dbcTest;

import com.teamride.messenger.server.dto.UserDTO;
import com.teamride.messenger.server.entity.UserEntity;
import com.teamride.messenger.server.repository.UserRepository;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Slf4j
@DataR2dbcTest
public class R2dbcTest {

	@Autowired
	UserRepository userRepository;

	@Test
	void userRepoTest() {
		Stream<UserEntity> userFlux = userRepository.findAll().toStream();

		userFlux.collect(Collectors.toList()).forEach(c -> log.info(c.toString()));
	}

	@Test
	void countByEmail() {
		Mono<Long> l = userRepository.countByEmail("shmin7777@naver.com");
		assertThat(1L).isEqualTo(l.block());
	}

	@Test
	void findByEmail() {
		Mono<UserEntity> monoUser = userRepository.findByEmail("shmin7777@naver.com");
		UserEntity user =  monoUser.block();
		log.info("user::"+user);
	}

	@Test
	void findByEmailAndPwd() {
		UserDTO userDTO = UserDTO.builder()
				.email("shmin7777@naver.com")
				.pwd("1234")
				.build();
		Mono<UserEntity>  monoUser = userRepository.findByEmailAndPwd(userDTO.getEmail(), userDTO.getPwd());
	
		UserEntity entity = monoUser.block();
		log.info("entity"+entity);
	}
}
