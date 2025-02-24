package com.augmentedcooking.Config.Security;

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
import lombok.ToString;

@Getter
@Configuration
@EnableConfigurationProperties(JwtSecretsConfig.JwtSecretProperties.class)
public class JwtSecretsConfig {

    private final JwtSecretProperties jwtSecretProperties;

    @Autowired
    public JwtSecretsConfig(JwtSecretProperties jwtSecretProperties) {
        this.jwtSecretProperties = jwtSecretProperties;
    }

    @Getter
    @Setter
    @Data
    @Validated
    @NoArgsConstructor
    @ConfigurationProperties(prefix = "jwt")
    public static class JwtSecretProperties {

        @NonNull
        private JwtKey access;

        @NonNull
        private JwtKey refresh;

        @Getter
        @Setter
        @Data
        @ToString
        public static class JwtKey {
            private String privateKey;
            private String publicKey;
        }
    }
}
