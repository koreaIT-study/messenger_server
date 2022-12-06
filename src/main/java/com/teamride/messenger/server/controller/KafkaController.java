package com.teamride.messenger.server.controller;

import com.teamride.messenger.server.service.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.web.bind.annotation.RestController;

import com.teamride.messenger.server.config.KafkaConstants;
import com.teamride.messenger.server.dto.ChatMessageDTO;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@RequiredArgsConstructor
public class KafkaController {
	private static final String topicName = "chat-client";
	private static final String chatServer = KafkaConstants.CHAT_SERVER;
	private final ChatService chatService;

	@Autowired
	private KafkaTemplate<String, ChatMessageDTO> kafkaTemplate;

	@KafkaListener(topics = chatServer, groupId = KafkaConstants.GROUP_ID)
	public void listen(ChatMessageDTO message, Acknowledgment ack) {
		log.info("Received Msg chatServer " + message);
		// client에서 message받음
		// message의 room id확인
		// 해당 room에 있는 사용자들의 id를 알아내서 send
		// topic : user id

		// message db저장
        chatService.insertMessage(message);

		kafkaTemplate.send(topicName, message);
		ack.acknowledge();
	}

}
