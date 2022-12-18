//package com.teamride.messenger.server.r2dbc;
//
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.data.annotation.Id;
//import org.springframework.data.repository.reactive.ReactiveCrudRepository;
//
//import lombok.AllArgsConstructor;
//import lombok.Data;
//import lombok.NoArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import reactor.core.publisher.Flux;
//
//@Slf4j
//@SpringBootTest
//public class R2dbcTest {
//
//	@Autowired
//	UserRepository userRepository;
//
//	@Test
//	void userRepoTest() {
//		Flux<User> userFlux = userRepository.findAll().log();
//		
//		userFlux.collectList().block().forEach(user->{
//			log.info("user::"+user);
//		});
//	}
//
//}
//
//@Data
//@AllArgsConstructor
//@NoArgsConstructor
//class User {
//
//	@Id
//	private int id; // uuid
//
//	private String email;
//	private String name;
//	private String pwd;
//	private String profilePath; // 나중에
//	private String profileImg;
//	private String profileOriginalImg;
//}
//
//interface UserRepository extends ReactiveCrudRepository<User, Integer> {
//
//}