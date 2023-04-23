package com.tokioschool.spring.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tokioschool.spring.domain.GuestReservation;

@Repository
public interface GuestReservationDAO extends JpaRepository<GuestReservation, Long>{
    
}
