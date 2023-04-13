package com.tokioschool.spring.security.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(JtwConfigurationProperties.class)
public class StoreConfiguration {

}
