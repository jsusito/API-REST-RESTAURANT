package com.tokioschool.spring.domain;

import java.util.Objects;
import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "roles")
public class Role {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	long id;
	@Column(nullable = false)
	String name;
	
	@Override
	public int hashCode() {
		
		return  Objects.hash(id, name);
	}
	
	@ManyToMany
	@JoinTable(name="users_roles", joinColumns = @JoinColumn(name="role_id"), inverseJoinColumns = @JoinColumn(name = "user_id"))
	Set<User> users;
	public String toString() {
		return name;
	}
}
