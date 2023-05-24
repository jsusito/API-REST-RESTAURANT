package com.tokioschool.spring.security.config;

import org.springframework.boot.context.properties.ConfigurationProperties;


@ConfigurationProperties(prefix ="jwt")
public record JwtConfigurationProperties(String secret, int expiredTime) {

}
