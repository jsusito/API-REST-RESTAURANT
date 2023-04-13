package com.tokioschool.spring.service;

import java.util.List;

import com.tokioschool.spring.domain.dto.ReservationDTO;

public interface ReservationService {
    
    void save(ReservationDTO reservation);

    void delete(long reservation);

    List<ReservationDTO> getReservations(String username);
}
