package com.teamride.messenger.server.repository;

import com.teamride.messenger.server.dto.FriendInfoDTO;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

import com.teamride.messenger.server.dto.UserDTO;
import com.teamride.messenger.server.entity.UserEntity;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface UserRepository extends ReactiveCrudRepository<UserEntity, Integer>{
	public Mono<Long> countByEmail(String email);
	
	public Mono<UserEntity> findByEmail(String email);
	
	public Mono<UserEntity> findByEmailAndPwd(String email, String pwd);
	
	@Query("SELECT F.FRIEND_ID\r\n"
			+ "		, U.NAME\r\n"
			+ "		, U.EMAIL\r\n"
			+ "		, IFNULL(U.PROFILE_PATH, '') PROFILE_PATH\r\n"
			+ "		, IFNULL(U.PROFILE_IMG, '') PROFILE_IMG\r\n"
			+ "		, IFNULL(ROOM.ROOM_ID, '') ROOM_ID"
			+ "	FROM FRIEND F\n" +
			"	JOIN USER U\n" +
			"	ON F.FRIEND_ID = U.ID\n" +
			"	LEFT JOIN (\n" +
			"		SELECT MEM.ROOM_ID\n" +
			"			, CR.ROOM_NAME\n" +
			"			, CR.IS_GROUP\n" +
			"			, CRM.ID\n" +
			"		FROM C_ROOM_MEMBER MEM\n" +
			"		JOIN C_ROOM CR\n" +
			"			ON MEM.ROOM_ID = CR.ROOM_ID\n" +
			"		JOIN C_ROOM_MEMBER CRM\n" +
			"			ON MEM.ROOM_ID = CRM.ROOM_ID\n" +
			"		WHERE MEM.ID = :userId\n" +
			"			AND CRM.ID <> :userId\n" +
			"			AND CR.IS_GROUP = 'N'\n" +
			"		) ROOM\n" +
			"	ON U.ID = ROOM.ID\n" +
			"	WHERE F.USER_ID = :userId")
	public Flux<FriendInfoDTO> findFriendsByUserid(int userId);
	
	@Query("SELECT \n" +
			"		u.id\n" +
			"		, u.email \n" +
			"		, u.name \n" +
			"		, u.profile_path \n" +
			"		, profile_img\n" +
			"	FROM \n" +
			"		`USER` u\n" +
			"	WHERE u.email = :searchKey \n" +
			"		OR u.name = :searchKey"
			)
	public Flux<UserDTO> findUserBySearchKey(String searchKey);
}
