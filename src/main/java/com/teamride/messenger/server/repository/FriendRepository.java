package com.teamride.messenger.server.repository;

import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

import com.teamride.messenger.server.dto.FriendDTO;
import com.teamride.messenger.server.entity.FriendEntity;

import reactor.core.publisher.Mono;

public interface FriendRepository extends ReactiveCrudRepository<FriendEntity, Void> {

//    @Query("INSERT INTO FRIEND (user_id, friend_id)\n" +
//            "VALUES (#{#param.userId}, #{#param.friendId})")
//    public Mono<FriendEntity> saveFriendEntity(@Param("param") FriendDTO param);
    @Query("INSERT INTO FRIEND (user_id, friend_id)\n" +
            "VALUES (:userId, :friendId)")
    public Mono<FriendEntity> saveFriendEntity(int userId, int friendId);
}
