package com.tokioschool.spring.service;

import java.util.Optional;

import com.tokioschool.spring.domain.User;
import com.tokioschool.spring.domain.dto.UserDTO;

public interface UserService {
	Optional<User> findById(Long id);
	UserDTO getUser(String username);
	Optional <User> findByUsername(String username);
	void save(User user);
}
