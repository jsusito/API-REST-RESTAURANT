package com.tokioschool.spring.security.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(JwtConfigurationProperties.class)
public class StoreConfiguration {

}
