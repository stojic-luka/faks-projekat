// package com.augmentedcooking.Config;

// import java.util.Arrays;
// import java.util.List;

// import org.springframework.context.annotation.Configuration;
// import
// org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
// import org.springframework.lang.NonNull;
// import
// org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
// import org.springframework.http.MediaType;
// import org.springframework.http.converter.HttpMessageConverter;

// @Configuration
// public class WebConfig extends WebMvcConfigurationSupport {

// @Override
// protected void configureMessageConverters(@NonNull
// List<HttpMessageConverter<?>> converters) {
// MappingJackson2HttpMessageConverter converter = new
// MappingJackson2HttpMessageConverter();
// converter.setSupportedMediaTypes(Arrays.asList(
// MediaType.APPLICATION_JSON,
// new MediaType("application", "*+json"),
// MediaType.APPLICATION_OCTET_STREAM));
// converters.add(converter);
// super.configureMessageConverters(converters);
// }
// }
