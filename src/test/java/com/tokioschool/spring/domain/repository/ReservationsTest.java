package com.tokioschool.spring.domain.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.tokioschool.spring.domain.Reservation;
import com.tokioschool.spring.domain.Role;
import com.tokioschool.spring.domain.User;
import com.tokioschool.spring.domain.proyection.ReservationCounterByDate;
import com.tokioschool.spring.domain.proyection.UserCounterByReservation;

@DataJpaTest
public class ReservationsTest {
    
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
            .username("jesus")
            .password("123456")
            .roles(new HashSet<>(List.of(userRole)))
            .build();
            
        User user2 = User.builder().name("salva")
            .surname("moreno")
            .birtDate(LocalDate.now())
            .email("salvador@gmail.com")
            .username("salvador")
            .password("123456")
            .roles(new HashSet<>(List.of(adminRole)))
            .build();

        User user3 = User.builder().name("alejandra")
        .surname("delgado")
        .birtDate(LocalDate.now())
        .email("conchi@gmail.com")
        .username("alejandra")
        .password("123456")
        .roles(new HashSet<>(List.of(adminRole)))
        .build();

            
        Reservation reservationUser1 = Reservation.builder()
            .user(user1)
            .numberPeople(4)
            .dateReservations(LocalDate.of(2023, 4, 26))
            .build();
        
        Reservation reservationUser1a = Reservation.builder()
        .user(user1)
        .numberPeople(5)
        .dateReservations(LocalDate.of(2023, 4, 26))
        .build();
        
        Reservation reservationUser2 = Reservation.builder()
            .user(user2)
            .numberPeople(2)
            .dateReservations(LocalDate.of(2023, 4, 27))
            .build();

        Reservation reservationUser2a = Reservation.builder()
        .user(user2)
        .numberPeople(2)
        .dateReservations(LocalDate.of(2023, 4, 27))
        .build();
    
        Reservation reservationUser2b = Reservation.builder()
        .user(user2)
        .numberPeople(6)
        .dateReservations(LocalDate.of(2023, 4, 27))
        .build();

        Reservation reservationUser3 = Reservation.builder()
        .user(user3)
        .numberPeople(6)
        .dateReservations(LocalDate.of(2023, 4, 28))
        .build();
        


        reservationDAO.saveAll(List.of(reservationUser1, reservationUser2, reservationUser1a, reservationUser2a, reservationUser2b, reservationUser3));
        
        user1.setReservations(new HashSet<>(List.of(reservationUser1, reservationUser1a)));
        userDAO.save(user1);

        user2.setReservations(new HashSet<>(List.of(reservationUser2,reservationUser2a,reservationUser2b)));
        userDAO.save(user2);
        
        user3.setReservations(new HashSet<>(List.of(reservationUser3)));
        userDAO.save(user3);
    }

    @AfterEach
	void afterEach() {
		userDAO.deleteAll();
        reservationDAO.deleteAll();
        roleDAO.deleteAll();
	
	}

    @Test
    public void givenListwhenFindUserCounter_thenOk(){
         List<UserCounterByReservation> countReservationsByUser = reservationDAO.getCountReservationsByUser();
         
         assertThat(countReservationsByUser.size() == 3);
            
         assertThat( countReservationsByUser
            .stream()
            .filter(reserva -> reserva.getUsername() == "salvador").findFirst().get())
                .returns("salvador", UserCounterByReservation::getUsername)
                .returns(3, UserCounterByReservation::getCounter);
    }

    @Test
    public void givenDateWhenCounterThenOk(){
        Optional<ReservationCounterByDate> getReservationCounterByDate = reservationDAO.getReservationCounterByDate(LocalDate.of(2023, 4, 26));

        assertThat(getReservationCounterByDate.get().getCounter() == 2);
        assertThat(getReservationCounterByDate.get().getDateReservation().toString().equals("2023-04-26")).isTrue();

    }
}
