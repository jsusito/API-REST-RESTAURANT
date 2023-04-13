package com.tokioschool.spring.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tokioschool.spring.domain.dto.UserDTO;
import com.tokioschool.spring.service.UserService;

import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class ControllerUser {
    
private final UserService userService;

    @GetMapping("/{username}")
    public ResponseEntity<UserDTO> getUser(@NotBlank @PathVariable String username){
        return ResponseEntity.ok(userService.getUser(username));
    }

}
