package com.augmentedcooking.Config;

import com.augmentedcooking.Config.Serialization.CUIDModule;
import com.fasterxml.jackson.databind.Module;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JacksonConfig {

	@Bean
	public Module cuidModule() {
		return new CUIDModule();
	}
}
