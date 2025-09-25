package com.example.demo.process.controller;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class StationWebSocketController {
    @MessageMapping("/hello")
    @SendTo("/topic/greetings")
    public String testMessage(String message) {
        return "Hello, " + message;
    }
}
