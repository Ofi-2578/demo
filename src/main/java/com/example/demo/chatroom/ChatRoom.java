package com.example.demo.chatroom;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document
public class ChatRoom {
    List<String> members;
    @Id
    String id;
    List<Message> messages;

    public ChatRoom(List<String> members, String id, List<Message> messages) {
        this.members = members;
        this.id = id;
        this.messages = messages;
    }

    public List<String> getMembers() {
        return members;
    }

    public void setMembers(List<String> members) {
        this.members = members;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<Message> getMessages() {
        return messages;
    }

    public void setMessages(List<Message> messages) {
        this.messages = messages;
    }
}
