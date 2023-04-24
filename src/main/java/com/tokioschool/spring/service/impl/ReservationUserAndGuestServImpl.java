package com.tokioschool.spring.service.impl;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.tokioschool.spring.domain.dto.ReservationUserAndGuest;
import com.tokioschool.spring.service.GuestReservationService;
import com.tokioschool.spring.service.ReservationService;
import com.tokioschool.spring.service.ReservationUserAndGuestService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ReservationUserAndGuestServImpl implements ReservationUserAndGuestService{

    private final ReservationService reservationService;
    private final GuestReservationService guestReservationService;
    private final ModelMapper modelMapper;
    
    @Override
    public List<ReservationUserAndGuest> getReservations(LocalDate date) {
        
        List<ReservationUserAndGuest> reservationsMix = new ArrayList<ReservationUserAndGuest>();

        reservationService.getReservationsByDate(date)
            .stream()
            .map(reservationDTO -> modelMapper.map(reservationDTO, ReservationUserAndGuest.class))
            .forEach(reservationsMix::add);
            
            
        guestReservationService.getReservations(date)
            .stream()
            .map(reservaGuest -> modelMapper.map(reservaGuest, ReservationUserAndGuest.class))
            .forEach(reservationsMix::add);

        reservationsMix.sort(Comparator.comparing(ReservationUserAndGuest::getLunchHour));
        return reservationsMix;
    }
    
}
