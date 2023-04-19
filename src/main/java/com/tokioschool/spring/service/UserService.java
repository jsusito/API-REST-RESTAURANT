package com.tokioschool.spring.service;

import java.time.LocalDateTime;
import java.util.Optional;

import com.tokioschool.spring.domain.User;
import com.tokioschool.spring.domain.dto.UserDTO;

public interface UserService {
	Optional<User> findById(Long id);
	UserDTO getUser(String username);
	Optional <User> findByUsername(String username);
	UserDTO save(User user);
	int updateInitSesion(String username, LocalDateTime lastLogin);
}
