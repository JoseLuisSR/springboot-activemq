package com.broker.activemq.entities;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Message {

    private String content;

    public Message(){

    }

    public Message(String content){
        this.content = content;
    }

    @Override
    public String toString(){
        return String.format("Message content: " + getContent());
    }
}
