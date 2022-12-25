package com.contactmanager.helper;

public class Message {

    private  String content;
    private String types;


    public String getTypes() {
        return types;
    }

    public void setTypes(String types) {
        this.types = types;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Message() {
    }

    public Message(String content, String types) {
        this.content = content;
        this.types = types;
    }
}
