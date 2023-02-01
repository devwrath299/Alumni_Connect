package com.example.instagramclone.model;

public class Chat {
    private  String message;
    private  String publisher;
    private  String reciver;
    private String MessageId;

    public Chat() {
    }

    public Chat(String message, String publisher, String reciver, String messageId) {
        this.message = message;
        this.publisher = publisher;
        this.reciver = reciver;
        MessageId = messageId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getReciver() {
        return reciver;
    }

    public void setReciver(String reciver) {
        this.reciver = reciver;
    }

    public String getMessageId() {
        return MessageId;
    }

    public void setMessageId(String messageId) {
        MessageId = messageId;
    }
}
