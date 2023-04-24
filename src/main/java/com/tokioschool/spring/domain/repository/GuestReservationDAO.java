package com.tokioschool.spring.domain.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tokioschool.spring.domain.GuestReservation;

@Repository
public interface GuestReservationDAO extends JpaRepository<GuestReservation, Long>{
    
    List<GuestReservation> findByDateReservations(LocalDate dateReservations);
}
