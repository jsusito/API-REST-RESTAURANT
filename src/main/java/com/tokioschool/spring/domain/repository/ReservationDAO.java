package com.tokioschool.spring.domain.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.tokioschool.spring.domain.Reservation;
import com.tokioschool.spring.domain.proyection.ReservationCounterByDate;
import com.tokioschool.spring.domain.proyection.UserCounterByReservation;

@Repository
public interface ReservationDAO extends JpaRepository<Reservation, Long> {
    
    List<Reservation> findByUserUsernameOrderByDateReservationsAscLunchHour(String username);
    
    List<Reservation> findByDateReservationsOrderByLunchHourAsc(LocalDate dateReservations);

    @Query("SELECT r.user.username AS username, COUNT(1) AS counter FROM reservations r GROUP BY r.user.username")
    List<UserCounterByReservation> getCountReservationsByUser();

    @Query("SELECT r.dateReservations AS dateReservation, COUNT(1) AS counter " +
           "FROM reservations r " + 
           "WHERE dateReservations =:date " + 
           "GROUP BY r.dateReservations")
    Optional<ReservationCounterByDate> getReservationCounterByDate(@Param("date") LocalDate date);

}
