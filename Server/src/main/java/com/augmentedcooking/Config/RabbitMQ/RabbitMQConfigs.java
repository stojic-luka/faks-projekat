package com.augmentedcooking.Config.RabbitMQ;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.lang.NonNull;
import org.springframework.validation.annotation.Validated;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Configuration
@EnableConfigurationProperties(RabbitMQConfigs.RabbitMQConfigsProperties.class)
public class RabbitMQConfigs {

    private final RabbitMQConfigsProperties rabbitMQConfigProperties;

    @Autowired
    public RabbitMQConfigs(final RabbitMQConfigsProperties rabbitMQConfigProperties) {
        this.rabbitMQConfigProperties = rabbitMQConfigProperties;
    }

    @Getter
    @Setter
    @Data
    @Validated
    @NoArgsConstructor
    @ConfigurationProperties(prefix = "rabbitmq")
    public static class RabbitMQConfigsProperties {

        @NonNull
        private String rabbitMQHost;

        @NonNull
        private String rabbitMQPort;

        @NonNull
        private String rabbitMQUser;

        @NonNull
        private String rabbitMQPass;

        @NonNull
        private String exchangeName;

        @NonNull
        private String requestQueue;

        @NonNull
        private String responseQueue;

        @NonNull
        private String requestRoutingKey;

        @NonNull
        private String responseRoutingKey;
    }
}
