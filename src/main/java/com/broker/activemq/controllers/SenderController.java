package com.broker.activemq.controllers;

import com.broker.activemq.entities.Message;
import com.broker.activemq.services.SenderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "${sender.context.path}")
public class SenderController {

    @Autowired
    public SenderService senderService;

    @PostMapping(consumes = {MediaType.APPLICATION_JSON_VALUE})
    public void send(@RequestBody Message message){
        senderService.send(message);
    }

}
