package com.tokioschool.spring.service;

import java.time.LocalDate;
import java.util.List;

import com.tokioschool.spring.domain.dto.ReservationDTO;

public interface ReservationService {
    
    void save(ReservationDTO reservation);

    void delete(long reservation);

    List<ReservationDTO> getReservationsByUsername(String username);
    List<ReservationDTO> getReservationsByDate(LocalDate date);

}
