package com.tokioschool.spring.controller;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.HashSet;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tokioschool.spring.domain.Role;
import com.tokioschool.spring.domain.User;
import com.tokioschool.spring.domain.dto.UserDTO;
import com.tokioschool.spring.service.RoleService;
import com.tokioschool.spring.service.UserService;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class ControllerUser {
    
private final UserService userService;
private final RoleService roleService;
private final PasswordEncoder passwordEncoder;


    @GetMapping("/{username}")
    public ResponseEntity<UserDTO> getUser(@NotBlank @PathVariable String username){
        return ResponseEntity.ok(userService.getUser(username));
    }
    
    @PostMapping("/new-user")
    public ResponseEntity<UserDTO> addUser(@RequestBody  @Valid UserDTO userDTO){
    	
    	//Por defecto todos los nuevos son usuarios sin privilegios
    	Role role = roleService.findByName("USER").get();
    	String pwdEncoder = passwordEncoder.encode(userDTO.getPassword());
    	
    	User user = User.builder()
    			.name(userDTO.getName())
    			.surname(userDTO.getSurname())
    			.password(pwdEncoder)
    			.username(userDTO.getUsername())
    			.email(userDTO.getEmail())
				.creationDate(LocalDate.now(ZoneId.systemDefault()))
    			.image(userDTO.getImage())
    			.birtDate(userDTO.getBirtDate())
    			.roles(new HashSet<>(List.of(role)))
    			.telephone(userDTO.getTelephone())
    			.active(true)
    			.build();
    	
    	 UserDTO currentUserDTO = userService.save(user);
    	
    	return ResponseEntity.ok(currentUserDTO);
    			
    	
    			
    }

}
