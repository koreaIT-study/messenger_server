package com.teamride.messenger.server.repository;

import com.teamride.messenger.server.dto.ChatMessageDTO;
import com.teamride.messenger.server.entity.ChatMessageEntity;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;

public interface ChatMessageRepository extends ReactiveCrudRepository<ChatMessageEntity, Void> {

    @Query("SELECT ROOM_ID, WRITER, MESSAGE,TIMESTAMP, NAME AS WRITER_NAME, M.EXTENSION, U.PROFILE_IMG " +
            "FROM C_MESSAGE M " +
            "JOIN USER U" +
            "   ON U.ID = M.WRITER " +
            "WHERE M.ROOM_ID = :roomId " +
            "ORDER BY TIMESTAMP  DESC " +
            "LIMIT 100")
    Flux<ChatMessageDTO> getAllMessage(String roomId);

    /**
     * 스크롤 끝까지 올렸을 때 이전 메시지 가져오기
     * */
    @Query("SELECT ROOM_ID, WRITER, MESSAGE,TIMESTAMP, NAME AS WRITER_NAME, M.EXTENSION, U.PROFILE_IMG " +
            "FROM C_MESSAGE M " +
            "JOIN USER U" +
            "   ON U.ID = M.WRITER " +
            "WHERE M.ROOM_ID = :#{#param.roomId} " +
            "AND TIMESTAMP < :#{#param.timestamp} " +
            "ORDER BY TIMESTAMP DESC " +
            "LIMIT 100")
    Flux<ChatMessageDTO> getBeforeMessage(@org.springframework.data.repository.query.Param("param") ChatMessageDTO param);
}
