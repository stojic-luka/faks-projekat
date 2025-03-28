package com.augmentedcooking.Config.RabbitMQ;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    public final RabbitMQConfigs.RabbitMQConfigsProperties rabbitMQConfigsProperties;

    @Autowired
    public RabbitMQConfig(final RabbitMQConfigs rabbitMQConfigs) {
        this.rabbitMQConfigsProperties = rabbitMQConfigs.getRabbitMQConfigProperties();
    }

    @Bean
    public ConnectionFactory connectionFactory() {
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory(
                rabbitMQConfigsProperties.getRabbitMQHost());
        connectionFactory.setPort(Integer.parseInt(rabbitMQConfigsProperties.getRabbitMQPort()));
        connectionFactory.setUsername(rabbitMQConfigsProperties.getRabbitMQUser());
        connectionFactory.setPassword(rabbitMQConfigsProperties.getRabbitMQPass());
        connectionFactory.setCacheMode(CachingConnectionFactory.CacheMode.CHANNEL);
        connectionFactory.setChannelCacheSize(25);
        return connectionFactory;
    }

    @Bean
    public TopicExchange aiExchange() {
        return new TopicExchange(rabbitMQConfigsProperties.getExchangeName());
    }

    @Bean
    public Queue requestQueue() {
        return new Queue(rabbitMQConfigsProperties.getRequestQueue(), true);
    }

    @Bean
    public Queue responseQueue() {
        return new Queue(rabbitMQConfigsProperties.getResponseQueue(), true);
    }

    @Bean
    public Binding requestBinding(Queue requestQueue, TopicExchange aiExchange) {
        return BindingBuilder
                .bind(requestQueue)
                .to(aiExchange)
                .with(rabbitMQConfigsProperties.getRequestRoutingKey());
    }

    @Bean
    public Binding responseBinding(Queue responseQueue, TopicExchange aiExchange) {
        return BindingBuilder
                .bind(responseQueue)
                .to(aiExchange)
                .with(rabbitMQConfigsProperties.getResponseRoutingKey());
    }

    @Bean
    public Jackson2JsonMessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public RabbitTemplate rabbitTemplate(
            ConnectionFactory connectionFactory,
            Jackson2JsonMessageConverter messageConverter) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(messageConverter);
        return rabbitTemplate;
    }
}
