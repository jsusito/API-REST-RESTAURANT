package com.tokioschool.spring.service.impl;

import java.time.LocalDateTime;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.tokioschool.spring.domain.User;
import com.tokioschool.spring.domain.dto.UserDTO;
import com.tokioschool.spring.domain.repository.UserDAO;
import com.tokioschool.spring.security.validations.Validations;
import com.tokioschool.spring.service.UserService;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiImpl implements UserService {

	@PostConstruct
	public void prueba(){
		log.trace("UserService,{}","trace");
		log.info("UserService,{}", "info");
	}
	
	
	private final UserDAO userRepository;
	private final ModelMapper modelMapper;
	
	@Override
	public Optional<User> findById(Long id) {

		return userRepository.findById(id);
	}
	@Override
	public Optional<User> findByUsername(String username) {
		
		return userRepository.findByUsername(username);
	}
	@Override
	public UserDTO save(User user) {
		
		if(findByEmail(user.getEmail()).isPresent())
			throw new IllegalArgumentException("Ya existe este email");
			
		
		User Currentuser = userRepository.save(user);
		return modelMapper.map(Currentuser, UserDTO.class);
		
	}
	@Override
	public UserDTO getUser(String username) {
		//Validations
		
		Validations.checkUser(username);

		User user = userRepository.findByUsername(username)
			.orElseThrow(()-> new NoSuchElementException("No existe este usuario")); 
		
		return modelMapper.map(user, UserDTO.class);
			
	}
	@Override
	public int updateInitSesion(String username, LocalDateTime lastLogin) {
		return userRepository.updateInitSesion(username, lastLogin);
	}
	@Override
	public Optional<User> findByEmail(String email) {
		return userRepository.findByEmail(email);
	}
	@Override
	public int totalUsers() {
		return (int) userRepository.count();
	}
	@Override
	public void deleteUser(Integer id) {
		userRepository.deleteById((long) id);
	}

}
