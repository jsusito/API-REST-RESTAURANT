package com.tokioschool.spring.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tokioschool.spring.domain.Role;
import com.tokioschool.spring.domain.User;
import com.tokioschool.spring.domain.dto.UserDTO;
import com.tokioschool.spring.domain.repository.RoleDAO;
import com.tokioschool.spring.domain.repository.UserDAO;
import com.tokioschool.spring.security.JwtRequestFilter;
import com.tokioschool.spring.security.JwtTokenUtil;
import com.tokioschool.spring.service.RoleService;
import com.tokioschool.spring.service.UserService;

@WebMvcTest(ControllerUser.class)
@ActiveProfiles("test")
public class ControllerUserTest {
    
    @Autowired WebApplicationContext context;
    @Autowired MockMvc mockMvc;
    @Autowired JwtRequestFilter jwtRequestFilter;

    @MockBean private UserService userService;
    @MockBean private RoleService  roleService;
    @MockBean private PasswordEncoder passwordEncoder;
    @MockBean JwtTokenUtil  jwtTokenUtil;
    @MockBean private RoleDAO roleDAO;
    @MockBean private UserDAO userDAO;

    @Autowired ObjectMapper objectMapper;
    
    @BeforeEach
    void setUp() {
        
        mockMvc = MockMvcBuilders.webAppContextSetup(context)
            .addFilter(jwtRequestFilter)
            .build();
}

@Test
void givenNewUserThenOk() throws Exception {
   
    UserDTO userRequest = UserDTO.builder()
            .name("jesus")
            .surname("ferrer")
            .birtDate(LocalDate.now().minusDays(10))
            .email("jsusito@gmail.com")
            .username("jesusFerrer")
            .password("123456")
            .build();
    
    UserDTO userResponse = UserDTO.builder()
        .id(25L)
        .name("jesus")
        .surname("ferrer")
        .birtDate(LocalDate.now().minusDays(10))
        .email("jsusito@gmail.com")
        .username("jesusFerrer")
        .password("XXXXXXXXX")
        .build();

    Role role = Role.builder().id(5L).name("USER").build();

    Mockito.when(roleService.findByName(anyString())).thenReturn(Optional.of(role));
    Mockito.when(passwordEncoder.encode("123456")).thenReturn("XXXXXXXXX");
    Mockito.when(userService.save(any(User.class))).thenReturn(userResponse);

    // Ejecución del test
    mockMvc.perform(post("/user/new-user")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(userRequest)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id").value(25))
            .andExpect(jsonPath("$.password").value("XXXXXXXXX"));

    // Verificaciones adicionales
    Mockito.verify(roleService).findByName("USER");
    Mockito.verify(passwordEncoder).encode("123456");
    Mockito.verify(userService).save(any(User.class));
    }
}
