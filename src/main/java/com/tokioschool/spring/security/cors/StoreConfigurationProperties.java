package com.tokioschool.spring.security.cors;

import org.springframework.boot.context.properties.ConfigurationProperties;


@ConfigurationProperties(prefix ="cors")
public record StoreConfigurationProperties(String permit) {

}
