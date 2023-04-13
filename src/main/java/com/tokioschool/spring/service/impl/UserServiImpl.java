package com.tokioschool.spring.service.impl;

import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.tokioschool.spring.domain.User;
import com.tokioschool.spring.domain.dto.UserDTO;
import com.tokioschool.spring.domain.repository.UserRepository;
import com.tokioschool.spring.service.UserService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServiImpl implements UserService {

	private final UserRepository userRepository;
	
	
	@Override
	public Optional<User> findById(Long id) {

		return userRepository.findById(id);
	}
	@Override
	public Optional<User> findByUsername(String username) {
		
		return userRepository.findByUsername(username);
	}
	@Override
	public void save(User user) {
		
		userRepository.save(user);
	}
	@Override
	public UserDTO getUser(String username) {
		//Validations
		User user = userRepository.findByUsername(username)
			.orElseThrow(()-> new NoSuchElementException("No existe este usuario")); 
		return 
			UserDTO.conveUserDTO(user);
	}

}
