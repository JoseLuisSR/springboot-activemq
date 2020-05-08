package com.broker.activemq.controllers;

import com.broker.activemq.services.PublisherCustomerService;
import com.broker.activemq.services.PublisherObjectService;
import com.broker.activemq.services.PublisherTextService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "${publisher.context.path}")
public class PublisherContoller {

    @Autowired
    private PublisherCustomerService customerService;

    @Autowired
    private PublisherTextService publisherTextService;

    @Autowired
    private PublisherObjectService publisherObjectService;

    @PostMapping(path = "${publisher.customer}")
    public void publishCustomer(@RequestParam("name") String name, @RequestParam("age") Integer age){
        customerService.publishCustomer(name, age);
    }

    @PostMapping(path = "${publisher.text}")
    public void publishText(@PathVariable("text") String text){
        publisherTextService.publishText(text);
    }

    @PostMapping(path = "${publisher.object}")
    public void publishObject(@PathVariable("value") String value){
        publisherObjectService.publishObject(value);
    }

}
