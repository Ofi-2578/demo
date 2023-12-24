package com.example.demo.chatroom;

public class Message {
    String sender;
    String content;
    long date; //stored as epoch

    public Message(String sender, String content, long date) {
        this.sender = sender;
        this.content = content;
        this.date = date;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }
}
