package com.broker.activemq.entities;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Customer {

    private String name;

    private Integer age;

    public Customer(){

    }

    public Customer(String name, Integer age){
        this.name = name;
        this.age = age;
    }

    @Override
    public String toString(){
        return String.format("Customer content: " + getName() + " " + getAge());
    }

}
