package com.tokioschool.spring.domain.repository;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.tokioschool.spring.domain.User;

import jakarta.transaction.Transactional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

	Optional<User> findByUsername(String username);

	@Modifying
	@Transactional
	@Query("UPDATE users u SET u.lastLogin = :lastLogin WHERE u.username = :username")
	int updateInitSesion(@Param("username") String username, @Param("lastLogin") LocalDateTime lastLogin);


}
