package com.tokioschool.spring.service.impl;

import java.time.LocalDate;
import java.util.List;

import org.modelmapper.ModelMapper;
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
    private final ModelMapper modelMapper;

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

    @Override
    public List<GuestReservationDTO> getReservations(LocalDate date) {
        return 
            guestReservationDAO.findByDateReservations(date)
            .stream()
            .map(reservationGuest ->
                 modelMapper.map(reservationGuest, GuestReservationDTO.class))
            .toList();

    }
    
}
