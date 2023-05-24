package com.tokioschool.spring.domain.repository;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.tokioschool.spring.domain.Reservation;
import com.tokioschool.spring.domain.Role;
import com.tokioschool.spring.domain.User;

@DataJpaTest

public class UserRepositoryTest {
    @Autowired UserDAO userDAO;
    @Autowired RoleDAO roleDAO;
    @Autowired ReservationDAO reservationDAO;
    
    @BeforeEach
    void before(){
        
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

           
        Reservation reservation1 = Reservation.builder()
            .user(user1)
            .numberPeople(4)
            .build();
        
        Reservation reservation2 = Reservation.builder()
            .user(user2)
            .numberPeople(2)
            .build();
        
        reservationDAO.saveAll(List.of(reservation1, reservation2));
        
        user1.setReservations(new HashSet<>(List.of(reservation1)));
        userDAO.save(user1);

        user2.setReservations(new HashSet<>(List.of(reservation2)));
        userDAO.save(user2);
     }

    @AfterEach
	void afterEach() {
		userDAO.deleteAll();
	}

    @Test
    void givenUser_whenFindAllAndDeleteReservation_thenReturnOk(){
        List<User> users = userDAO.findAll();
        Assertions.assertEquals(2, users.size());

        int total = (int) reservationDAO.findAll().stream().count();
        Assertions.assertEquals(2, total);

        long idUser = users.get(0).getId();

        //Verify delete in cascade
        userDAO.deleteById(idUser);
        int totalReservations = (int) reservationDAO.findAll().stream().count();
        Assertions.assertEquals(1, totalReservations);
    }

}
