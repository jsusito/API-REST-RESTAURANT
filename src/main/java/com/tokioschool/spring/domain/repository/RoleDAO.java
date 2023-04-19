package com.tokioschool.spring.domain.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tokioschool.spring.domain.Role;

@Repository
public interface RoleDAO extends JpaRepository<Role, Long> {

	Optional<Role> findByName(String role);

}
