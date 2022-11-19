package com.teamride.messenger.server.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
public class ChatRoomDTO {

    private String roomId; //pk
    private String roomName;
    private String isGroup;

    private List<String> userId;

    public static ChatRoomDTO create(String roomName, String isGroup){
        ChatRoomDTO room = new ChatRoomDTO();

        room.setRoomId(UUID.randomUUID().toString());
        room.setRoomName(roomName);
        room.setIsGroup(isGroup);
        return room;
    }

    public static ChatRoomDTO create(ChatRoomDTO room2){
        ChatRoomDTO room = new ChatRoomDTO();
        room.setRoomId(UUID.randomUUID().toString());
        room.setRoomName(room2.getRoomName());
        room.setIsGroup(room2.getIsGroup());
        return room;
    }
}
