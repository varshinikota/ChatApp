package com.example.chatapp;

public class Message {
    public String sender;
    public String message;
    public String timestamp;

    public Message(String sender, String message, String timestamp) {
        this.sender = sender;
        this.message = message;
        this.timestamp = timestamp;
    }
}
