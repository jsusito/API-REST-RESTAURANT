package com.tokioschool.spring.services;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import com.tokioschool.spring.domain.Role;
import com.tokioschool.spring.domain.User;
import com.tokioschool.spring.domain.repository.RoleDAO;
import com.tokioschool.spring.domain.repository.UserDAO;
import com.tokioschool.spring.service.UserService;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")

public class UserServiceTest {
    
    @Autowired private RoleDAO roleDAO;
    @Autowired private UserDAO userDAO;
    @Autowired private UserService userService;
    

    @BeforeEach
    void setUp() {
        Role adminRole = Role.builder().name("ADMIN").build();
        Role userRole = Role.builder().name("USER").build();
        roleDAO.saveAll(List.of(adminRole, userRole));
        
        User user1 = User.builder().name("jesus")
            .surname("ferrer")
            .birtDate(LocalDate.now().minusDays(10))
            .email("jsusito@gmail.com")
            .username("jesusFerrer")
            .password("123456")
            .roles(new HashSet<>(List.of(userRole)))
            .build();
            
        User user2 = User.builder().name("salva")
            .surname("moreon")
            .birtDate(LocalDate.now())
            .email("salvador@gmail.com")
            .username("salvadorFerrer")
            .password("123456")
            .roles(new HashSet<>(List.of(adminRole)))
            .build();
        
        userDAO.save(user1);
        userDAO.save(user2);
    }

    @AfterEach
    void deleteAll() {
        userDAO.deleteAll();
        roleDAO.deleteAll();

    }


    @Test
    void givenNewUser_whenCreate_thenUserCreatedAnd_givenNewUser_whenExists_thenUserNoCreate() {
        User user = User.builder().name("juan")
            .surname("gomez")
            .birtDate(LocalDate.now().minusDays(10))
            .email("juan@gmail.com")
            .username("juangomez")
            .password("123456")
            .build();
        
        userDAO.save(user);
        Assertions.assertEquals(3, userDAO.count());

        //Creamos el mismo usuario
        User user2 = User.builder().name("juan")
            .surname("gomez")
            .birtDate(LocalDate.now().minusDays(10))
            .email("juan@gmail.com")
            .username("juangomez")
            .password("123456")
            .build();

        try{
            userService.save(user2);
        }catch(Exception e){
            System.out.println("No se creo el usuario esta repetido");
        }
        
        Assertions.assertEquals(3, userDAO.count());

    }

}
