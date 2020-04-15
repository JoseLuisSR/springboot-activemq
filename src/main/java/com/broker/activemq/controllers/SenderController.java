package com.broker.activemq.controllers;

import com.broker.activemq.entities.Message;
import com.broker.activemq.services.SenderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "${sender.context.path}")
public class SenderController {

    @Autowired
    public SenderService senderService;

    @PostMapping(path = "${sender.sendMessage}")
    public void send(@PathVariable("message") String message){
        senderService.send(new Message(message));
    }

    @PostMapping(consumes = {MediaType.APPLICATION_JSON_VALUE})
    public void send(@RequestBody Message message){
        senderService.send(message);
    }

}
