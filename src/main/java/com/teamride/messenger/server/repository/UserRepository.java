package com.teamride.messenger.server.repository;

import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

import com.teamride.messenger.server.dto.UserDTO;
import com.teamride.messenger.server.entity.UserEntity;

import reactor.core.publisher.Mono;

public interface UserRepository extends ReactiveCrudRepository<UserEntity, Integer>{
	public Mono<Long> countByEmail(String email);
	
	public Mono<UserEntity> findByEmail(String email);
	
	public Mono<UserEntity> findByEmailAndPwd(String email, String pwd);
}
