package com.augmentedcooking.Config.Database;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.convert.MongoCustomConversions;

import com.augmentedcooking.Config.Converters.BytesToCUIDConverter;
import com.augmentedcooking.Config.Converters.CUIDToBytesConverter;

@Configuration
public class MongoConfig {

    @Bean
    public MongoCustomConversions mongoCustomConversions() {
        return new MongoCustomConversions(
                List.of(new CUIDToBytesConverter(), new BytesToCUIDConverter()));
    }
}
