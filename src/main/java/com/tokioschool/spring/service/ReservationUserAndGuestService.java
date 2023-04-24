package com.tokioschool.spring.service;

import java.time.LocalDate;
import java.util.List;

import com.tokioschool.spring.domain.dto.ReservationUserAndGuest;

public interface ReservationUserAndGuestService {

    List<ReservationUserAndGuest> getReservations(LocalDate date);
}
