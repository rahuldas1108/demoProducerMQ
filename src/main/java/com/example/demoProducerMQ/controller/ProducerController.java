package com.example.demoProducerMQ.controller;

import com.example.demoProducerMQ.dtos.User;
import com.example.demoProducerMQ.services.RabbitMqProducerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class ProducerController {

    private RabbitMqProducerService rabbitMqProducerService;

    public ProducerController(RabbitMqProducerService rabbitMqProducerService) {
        this.rabbitMqProducerService = rabbitMqProducerService;
    }

    @PostMapping("/publish")
    public ResponseEntity<String> getMessage(@RequestBody User user){
        rabbitMqProducerService.sendJsonMessage(user);
        return ResponseEntity.ok("User details sent to RabbitMQ ...");
    }
}
