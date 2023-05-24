package com.tokioschool.spring.security;

import static org.springframework.security.config.Customizer.withDefaults;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableMethodSecurity //que active la configuracion del Bean SecurityFilterChain
@RequiredArgsConstructor
public class WebSecurityConfig  {
	
	//Esta clase se encarga de mostrar el error
	final private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
	final private JwtRequestFilter jwtRequestFilter;
		
	@Bean 
	public SecurityFilterChain configureFilterChain(HttpSecurity httpSecurity) throws Exception {
		
		return httpSecurity
                .authorizeHttpRequests(requests -> requests.requestMatchers(
                    "/login"
	                , "/swagger-ui-custom.html"
	                , "/swagger-ui/**"
	                , "/swagger-resources"
	                , "/configuration/security"
	                , "/configuration/ui"
					, "/reservation/guest"
					, "/reservation/date/**"
					, "/api-docs/**"
					, "/testExchangeAuthentication")
                        .permitAll())
				.authorizeHttpRequests(requests -> requests.requestMatchers(
					"/user/new-user").hasAuthority("ADMIN")
					.anyRequest().authenticated())
                .exceptionHandling(handling -> handling.authenticationEntryPoint(jwtAuthenticationEntryPoint))
                .sessionManagement(management -> management.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .csrf(csrf -> csrf.disable())
                .cors(withDefaults())
                //Que antes de ejecutarse el filtro de spring para todo el logado ejecute el nuestro el jwtRequestFilter
                .addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
		}
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
		
	//Implementa un Bean que devuelve un objeto AuthenticationManager
	//Este objeto AuthenticationManager se encarga de validar las credenciales del usuario
	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
		return authenticationConfiguration.getAuthenticationManager();
	}
	
	
	
}
