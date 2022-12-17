package com.teamride.messenger.server.controller;

import com.teamride.messenger.server.service.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.kafka.support.SendResult;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.web.bind.annotation.RestController;

import com.teamride.messenger.server.config.KafkaConstants;
import com.teamride.messenger.server.dto.ChatMessageDTO;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@RequiredArgsConstructor
public class KafkaController {
	private final ChatService chatService;

	@Autowired
	private KafkaTemplate<String, ChatMessageDTO> kafkaTemplate;

	@KafkaListener(topics = KafkaConstants.CHAT_SERVER, groupId = KafkaConstants.GROUP_ID)
	public void listen(ChatMessageDTO message, Acknowledgment ack) {
		log.info("Received Msg chatServer " + message);
		// client에서 message받음
		// message의 room id확인
		// 해당 room에 있는 사용자들의 id를 알아내서 send
		// topic : user id

		// message db저장
		chatService.insertMessage(message);

		ListenableFuture<SendResult<String, ChatMessageDTO>> future=kafkaTemplate.send(KafkaConstants.CHAT_CLIENT, "message", message);
		
		future.addCallback((result)->{
		    log.info("message 전송 성공, message :: {}, result is :: {}", message, result);
		}, (ex)->{
		    log.error("message 전송 실패, message :: {}, error is :: {}", message, ex);
		});
		ack.acknowledge();
	}

}
