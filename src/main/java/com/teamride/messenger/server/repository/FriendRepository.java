package com.teamride.messenger.server.repository;

import com.teamride.messenger.server.dto.FriendDTO;
import com.teamride.messenger.server.entity.FriendEntity;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

public interface FriendRepository extends ReactiveCrudRepository<FriendEntity, Void> {

    @Query("INSERT INTO FRIEND (user_id, friend_id)\n" +
            "VALUES (#{#param.userId}, #{#param.friendId})")
    public Mono<FriendEntity> saveFriendEntity(@Param("param") FriendDTO param);
}
