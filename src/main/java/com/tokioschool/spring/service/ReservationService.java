package com.tokioschool.spring.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import com.tokioschool.spring.domain.dto.ReservationDTO;
import com.tokioschool.spring.domain.proyection.ReservationCounterByDate;
import com.tokioschool.spring.domain.proyection.UserCounterByReservation;

public interface ReservationService {
    
    void save(ReservationDTO reservation);

    void delete(long reservation);

    List<ReservationDTO> getReservationsByUsername(String username);
    List<ReservationDTO> getReservationsByDate(LocalDate date);
    List<UserCounterByReservation> getUserCounterByReservations();
    Optional<ReservationCounterByDate> getReservationCounterByDate(LocalDate date);
}
