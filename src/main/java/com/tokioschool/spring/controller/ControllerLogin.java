package com.tokioschool.spring.controller;

import java.time.LocalDateTime;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.tokioschool.spring.security.JwtRequest;
import com.tokioschool.spring.security.JwtResponse;
import com.tokioschool.spring.security.JwtTokenUtil;
import com.tokioschool.spring.service.UserService;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@CrossOrigin
public class ControllerLogin {

	private final JwtTokenUtil jwtTokenUtil;
	private final UserDetailsService userDetailsService;
	private final PasswordEncoder passwordEncoder;
	private final UserService userService;
	private final AuthenticationManager authenticationManager;

	@Tag(name = "Login", description = "Authentication (get the Bearer)")
	@PostMapping(value = "/login", consumes = "application/json", produces = "application/json")
	public ResponseEntity<?> login(@RequestBody JwtRequest authRequest) throws Exception {

		// comprueba que el usuario y contraseña existan, llama internamente a
		// FilterSecurity y a userDetails.
		authenticate(authRequest.getUsername(), authRequest.getPassword());

		userService.updateInitSesion(authRequest.getUsername(), LocalDateTime.now());

		final String token = jwtTokenUtil.generateToken(authRequest.getUsername());

		return ResponseEntity.ok(new JwtResponse(token));

	}

	private void authenticate(String username, String password) throws Exception {
		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
			
		} catch (DisabledException de) {
			throw new Exception("User disable", de);
		} catch (BadCredentialsException bce) {
			throw new Exception("Invalid credentials", bce);
		}
	}

}
