package com.tokioschool.spring.security.cors;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class CorsConfiguration implements WebMvcConfigurer{
	final private StoreConfigurationProperties configCors;
	
	@Override
	public void addCorsMappings(CorsRegistry registry) {
		
		registry
			.addMapping("/recetas/**")
			.allowedMethods(HttpMethod.GET.name(), HttpMethod.POST.name())
			.allowedOrigins(configCors.permit()); 
		
		registry
			.addMapping("/user/**")
			.allowedMethods(HttpMethod.GET.name(), HttpMethod.POST.name())
			.allowedOrigins(configCors.permit()); 
		registry
			.addMapping("/reservation/**")
			.allowedMethods(HttpMethod.GET.name(), HttpMethod.POST.name())
			.allowedOrigins(configCors.permit()); 
	}
	
}
