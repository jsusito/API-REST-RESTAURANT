package com.tokioschool.spring.service.impl;

import org.springframework.stereotype.Service;

import com.tokioschool.spring.domain.GuestReservation;
import com.tokioschool.spring.domain.dto.GuestReservationDTO;
import com.tokioschool.spring.domain.repository.GuestReservationDAO;
import com.tokioschool.spring.service.GuestReservationService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class GuestReservationServiceImpl implements GuestReservationService{

    private final GuestReservationDAO guestReservationDAO;

    @Override
    public void save(GuestReservationDTO guestReservationDTO) {
        GuestReservation guestReservation = GuestReservation.builder()
            .dateReservations(guestReservationDTO.getDateReservations())
            .dinnerTable(guestReservationDTO.getDinnerTable())
            .lunchHour(guestReservationDTO.getLunchHour())
            .surname(guestReservationDTO.getSurname())
            .telephone(guestReservationDTO.getTelephone())
            .username(guestReservationDTO.getUsername())
            .numberPeople(guestReservationDTO.getNumberPeople())
            .build();

        guestReservationDAO.save(guestReservation);
    }
    
}
