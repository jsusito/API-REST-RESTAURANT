package com.tokioschool.spring.domain.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tokioschool.spring.domain.Reservation;

@Repository
public interface ReservationDAO extends JpaRepository<Reservation, Long> {
    List<Reservation> findByUserUsernameOrderByDateReservationsAscDinnerHour(String username);
    
    List<Reservation> findByDateReservationsOrderByDinnerHourAsc(LocalDate dateReservations);
}
