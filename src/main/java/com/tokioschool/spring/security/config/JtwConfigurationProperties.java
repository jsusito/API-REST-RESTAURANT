package com.tokioschool.spring.security.config;

import org.springframework.boot.context.properties.ConfigurationProperties;


@ConfigurationProperties(prefix ="jwt")
public record JtwConfigurationProperties(String secret, int expiredTime) {

}
