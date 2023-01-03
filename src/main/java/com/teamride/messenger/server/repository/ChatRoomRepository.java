package com.teamride.messenger.server.repository;

import java.util.Map;

import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

import com.teamride.messenger.server.dto.ChatRoomDTO;
import com.teamride.messenger.server.entity.ChatRoomEntity;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ChatRoomRepository extends ReactiveCrudRepository<ChatRoomEntity, String>{
	/**
	 * 로그인한 user의 모든 채팅방 조회
	 * @param userId
	 * @return
	 */
	@Query("WITH MSG AS (\r\n"
			+ "		SELECT\r\n"
			+ "			MSG.room_id\r\n"
			+ "			, MSG.message\r\n"
			+ "			, T.timestamp\r\n"
			+ "		FROM C_MESSAGE MSG\r\n"
			+ "		JOIN (\r\n"
			+ "			SELECT\r\n"
			+ "				room_id\r\n"
			+ "				, MAX(timestamp) TIMESTAMP\r\n"
			+ "			FROM C_MESSAGE\r\n"
			+ "			GROUP BY room_id\r\n"
			+ "			) T\r\n"
			+ "		ON MSG.ROOM_ID = T.ROOM_ID\r\n"
			+ "		AND MSG.TIMESTAMP = T.timestamp\r\n"
			+ "		)\r\n"
			+ "		SELECT MSG.ROOM_ID\r\n"
			+ "			, MSG.MESSAGE\r\n"
			+ "			, MSG.timestamp\r\n"
			+ "			, RNM.ROOM_NAME\r\n"
			+ "			, CNT.CNT\r\n"
			+ "			, RNM.ROOM_IMAGE_PATH\r\n"
			+ "		FROM MSG\r\n"
			+ "		JOIN (\r\n"
			+ "		      SELECT\r\n"
			+ "		        CRM.ROOM_ID\r\n"
			+ "				, MAX(R.ROOM_NAME) ROOM_NAME\r\n"
			+ "				, MAX(ROOM_IMAGE_PATH) ROOM_IMAGE_PATH\r\n"
			+ "		      FROM C_ROOM R\r\n"
			+ "		      JOIN C_ROOM_MEMBER CRM\r\n"
			+ "		          ON R.ROOM_ID = CRM.ROOM_ID\r\n"
			+ "		          AND CRM.ID = :userId \r\n"
			+ "			  GROUP BY R.ROOM_ID\r\n"
			+ "		) RNM\r\n"
			+ "		ON MSG.ROOM_ID = RNM.ROOM_ID\r\n"
			+ "		JOIN (\r\n"
			+ "			SELECT ROOM_ID\r\n"
			+ "				, COUNT(ID) CNT\r\n"
			+ "			FROM C_ROOM_MEMBER\r\n"
			+ "			GROUP BY ROOM_ID\r\n"
			+ "		) CNT\r\n"
			+ "		ON CNT.ROOM_ID = MSG.ROOM_ID\r\n"
			+ "		ORDER BY timestamp DESC")
	public Flux<ChatRoomDTO> getAllRoom(String userId);
	
	/**
	 * message가 없어도 해당 room만 return
	 * @param roomId
	 * @return
	 */
	@Query(" SELECT * FROM C_ROOM CR JOIN\r\n"
			+ "             (SELECT room_id, COUNT(id) AS cnt\r\n"
			+ "		    FROM C_ROOM_MEMBER\r\n"
			+ "		    GROUP BY room_id) CRM\r\n"
			+ "            ON CR.room_id = CRM.room_id\r\n"
			+ "			where CR.room_id = :roomId")
	public Mono<ChatRoomDTO> getRoom(String roomId);
	
	/**
	 * room을 찾고, 해당 room에 user가 누가 있는지 찾아주는 쿼리(무조건 message가 있어야 함)
	 * @param roomId
	 * @return
	 */
	@Query("SELECT msg.room_id\r\n"
			+ "		    , msg.message\r\n"
			+ "		    , msg.timestamp\r\n"
			+ "		    , room.room_name\r\n"
			+ "		    , mem.cnt\r\n"
			+ "		FROM (\r\n"
			+ "		    SELECT  room_id, message, timestamp \r\n"
			+ "		    FROM C_MESSAGE\r\n"
			+ "		    WHERE room_id = :roomId\r\n"
			+ "		    ORDER BY timestamp DESC\r\n"
			+ "		    LIMIT 1) msg\r\n"
			+ "		JOIN C_ROOM room\r\n"
			+ "		    ON msg.room_id = room.room_id\r\n"
			+ "		JOIN (\r\n"
			+ "		    SELECT room_id, COUNT(id) AS cnt\r\n"
			+ "		    FROM C_ROOM_MEMBER\r\n"
			+ "		    GROUP BY room_id\r\n"
			+ "		    ) mem\r\n"
			+ "		    on msg.room_id = mem.room_id")
	public Mono<ChatRoomDTO> getRoomWithUsers(String roomId);
	
	/**
	 * 해당 방의 모든 USER ID를 찾는 QUERY
	 * @param roomId
	 * @return
	 */
	@Query("SELECT ID\r\n"
			+ "		FROM C_ROOM_MEMBER\r\n"
			+ "		WHERE ROOM_ID = :roomId")
	public Flux<String> getRoomMember(String roomId);
	
	/**
	 * 단톡방이 아닌 1:1 채팅방 찾는 query
	 * @param param
	 * @return
	 */
	@Query("SELECT CR.ROOM_ID\r\n"
			+ "			 , CR.ROOM_NAME\r\n"
			+ "			 , CR.IS_GROUP\r\n"
			+ "		FROM C_ROOM CR\r\n"
			+ "		JOIN C_ROOM_MEMBER CRM1\r\n"
			+ "		  ON CR.ROOM_ID = CRM1.ROOM_ID\r\n"
			+ "		JOIN C_ROOM_MEMBER CRM2\r\n"
			+ "		  ON CRM1.ROOM_ID = CRM2.ROOM_ID\r\n"
			+ "		WHERE CR.IS_GROUP = 'N'\r\n"
			+ "		  AND CRM1.ID = :myId\r\n"
			+ "		  AND CRM2.ID = :friendId")
	public Mono<ChatRoomDTO> getOneByOneRoom(String myId, String friendId);
	
	/**
	 * C_ROOM_MEMBER TABLE에 저장하는 QUERY
	 * @param roomId
	 * @param id
	 * @return
	 */
	@Query("INSERT INTO C_ROOM_MEMBER(room_id, ID)\r\n"
			+ "		VALUES\r\n"
			+ "			(:roomId, :id)")
	public Mono<Integer> insertRoomMember(String roomId, String id);
	
	@Query("UPDATE C_ROOM SET ROOM_IMAGE_PATH = :path WHERE ROOM_ID = :roomId")
	public Mono<Void> updateRoomImagePathByRoomId(String path, String roomId);
}
