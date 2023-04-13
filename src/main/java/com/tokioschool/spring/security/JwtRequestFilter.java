package com.tokioschool.spring.security;

import java.io.IOException;
import java.text.ParseException;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.nimbusds.jose.JOSEException;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtRequestFilter extends OncePerRequestFilter {

	private final JwtTokenUtil jwtTokenUtil;
	private final UserDetailsService userDetailsService;
	
	//Cada vez que se lanza una peticion spring hace que pase por aqui
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
		//Cada vez que interceptamos una peticion preguntamos por este atributo que es el que tiene el token
		final String requestToken = request.getHeader("Authorization");
		
		String username = null;
		String jwtToken = null;
		
		//Por convencion los token JSON vienen asi "Bearer " y la clave cifrada
		if(requestToken != null && requestToken.startsWith("Bearer ")) {
			jwtToken = requestToken.substring(7);
			try {
				username = jwtTokenUtil.getUsername(jwtToken);
			}catch(IllegalArgumentException iae) {
				log.error("No se pudo obtener el token");
			}catch(Exception e) {
				log.error("token expirado");
			}
		} 
		else {
			log.warn("Token sin Bearin delante");                                                                  
		}
		
		//Si hay usuario 
		if(username !=null) {
			//Comprobamos que el usuario este en la base de datos.
			UserDetails userDetails = userDetailsService.loadUserByUsername(username);
			
			//Que el usuario sea el mismo que el token en la base de datos
			try {
				if(jwtTokenUtil.validateToken(jwtToken, userDetails)) {
					
					//metemos informacion de peticion de la peticion(request)
					UsernamePasswordAuthenticationToken userPassAuthentToken = 
							new UsernamePasswordAuthenticationToken(userDetails,null , userDetails.getAuthorities());
					
					userPassAuthentToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
					
					//metemos toda esta informacion en el contexto de seguridad para que pueda entrar y no validarse otra vez pero en el segundo filtro
					//El filtro que va despues de RequestFilter, filtro de spring Security
					SecurityContextHolder.getContext().setAuthentication(userPassAuthentToken);
				}
			} catch (ParseException e) {
				
				log.error(e.getMessage());
			} catch (JOSEException e) {
				
				log.error(e.getMessage());
			}
		}
		//
		//Si está autenticado lo dejara pasar,  y si no no lo dejara pasar a no ser que valla 
		//al /login, y ya le da paso al filtro de SpringSecurity.
		filterChain.doFilter(request, response);
	}
	
}

