package com.example.demoProducerMQ.configuration;


import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Declarables;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;

import java.util.List;
import java.util.stream.Collectors;

@Configuration
public class RabbitMqConfig {

    @Value("${producer.exchange.name}")
    private String exchange;

    @Value("${producer.dl-exchange.name}")
    private String dlExchange;

    @Value("${producer.queue.name}")
    private String queue;

    @Value("${producer.queue.routing-keys}")
    private String routingKeys;

    @Value("${producer.dead-letter-queue.name}")
    private String deadLetterQueue;

    @Value("${producer.dead-letter-queue.routing-key}")
    private String deadLetterRoutingKey;

    @Value("${producer.retry-queue.name}")
    private String retryQueue;

    @Value("${producer.retry-queue.routing-key}")
    private String retryRoutingKey;

    @Value("${producer.parking-lot-queue.name}")
    private String parkingLotQueue;

    @Value("${producer.parking-lot-queue.routing-key}")
    private String parkingLotRoutingKey;

    @Value("${producer.message-ttl}")
    private int timeToLive;


    @Bean
    Queue primaryQueue() {
        return QueueBuilder.durable(queue).withArgument("x-dead-letter-exchange", dlExchange)
                .withArgument("x-dead-letter-routing-key", parkingLotRoutingKey).build();
    }

    @Bean
    TopicExchange exchange() {
        return new TopicExchange(exchange);
    }

    @Bean
    Binding binding(Queue queue, TopicExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(routingKeys);
    }
    @Bean
    Binding retryQueueBinding(TopicExchange deadLetterExchange) {
        return BindingBuilder.bind(new Queue(retryQueue)).to(deadLetterExchange).with(retryRoutingKey);
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate template = new RabbitTemplate(connectionFactory);
        template.setMessageConverter(new Jackson2JsonMessageConverter());
        return template;
    }
}

