package com.broker.activemq.controllers;

import com.broker.activemq.entities.CustomClass;
import com.broker.activemq.entities.Customer;
import com.broker.activemq.services.ProducerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "${producer.context.path}")
public class ProducerController {

    @Autowired
    ProducerService producerService;

    @PostMapping(path = "${producer.send.customer}")
    public void sendCustomer(@RequestParam("name") String name, @RequestParam("age") Integer age){
        producerService.sendCustomer(new Customer(name, age));
    }

    @PostMapping(path = "${producer.send.text}")
    public void sendText(@PathVariable("text") String text){
        producerService.sendText(text);
    }

    @PostMapping(path = "${producer.send.object}")
    public void sendObject(@PathVariable("value") String value){
        producerService.sendObject(new CustomClass(value));
    }

}
