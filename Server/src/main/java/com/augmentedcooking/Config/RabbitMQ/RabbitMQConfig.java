package com.augmentedcooking.Config.RabbitMQ;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    // Define constants for easy management.
    public static final String EXCHANGE_NAME = "my_exchange";
    public static final String ROUTING_KEY = "my_routing_key";
    public static final String QUEUE_NAME = "my_queue";

    @Bean
    public Queue myQueue() {
        // The 'true' argument makes the queue durable.
        return new Queue(QUEUE_NAME, true);
    }

    @Bean
    public DirectExchange myExchange() {
        // Durable exchange that persists through restarts.
        return new DirectExchange(EXCHANGE_NAME, true, false);
    }

    @Bean
    public Binding binding(Queue myQueue, DirectExchange myExchange) {
        // Bind the queue to the exchange using the routing key.
        return BindingBuilder.bind(myQueue).to(myExchange).with(ROUTING_KEY);
    }
}
