package com.broker.activemq.controllers;

import com.broker.activemq.services.ProducerCustomerService;
import com.broker.activemq.services.ProducerObjectService;
import com.broker.activemq.services.ProducerTextService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "${producer.context.path}")
public class ProducerController {

    @Autowired
    private ProducerCustomerService producerCustomerService;

    @Autowired
    private ProducerTextService producerTextService;

    @Autowired
    private ProducerObjectService producerObjectService;

    @PostMapping(path = "${producer.send.customer}")
    public void sendCustomer(@RequestParam("name") String name, @RequestParam("age") Integer age){
        producerCustomerService.sendCustomer(name, age);
    }

    @PostMapping(path = "${producer.send.text}")
    public void sendText(@PathVariable("text") String text){
        producerTextService.sendText(text);
    }

    @PostMapping(path = "${producer.send.object}")
    public void sendObject(@PathVariable("value") String value){
        producerObjectService.sendObject(value);
    }

}
