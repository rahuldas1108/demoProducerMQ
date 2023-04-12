package com.example.demoProducerMQ.services;

import com.example.demoProducerMQ.dtos.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class RabbitMqProducerService {

    @Value("${producer.exchange.name}")
    private String exchange;

    @Value("${producer.queue.routing-keys}")
    private String routingJsonKey;


    private final RabbitTemplate rabbitTemplate;

    public RabbitMqProducerService(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }


    public void sendJsonMessage(User user){
        log.info("User details sending to MQ", user.toString());
        rabbitTemplate.convertAndSend(exchange, routingJsonKey, user);
    }
}
