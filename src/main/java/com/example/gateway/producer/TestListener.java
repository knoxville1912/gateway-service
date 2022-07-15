package com.example.gateway.producer;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Component
public class TestListener {

    @JmsListener(destination = "Test")
    void listen(String message) {
        System.out.println("----" + message + "----");
    }
}
