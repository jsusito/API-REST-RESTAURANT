package com.tokioschool.spring.service;

import java.time.LocalDate;
import java.util.List;

import com.tokioschool.spring.domain.dto.GuestReservationDTO;

public interface GuestReservationService {
    
    public void save(GuestReservationDTO guestReservationDTO);
    public List<GuestReservationDTO> getReservations(LocalDate date);
}
