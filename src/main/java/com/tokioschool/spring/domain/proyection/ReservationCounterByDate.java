package com.tokioschool.spring.domain.proyection;

import java.time.LocalDate;

public interface ReservationCounterByDate {
    LocalDate getDateReservation();
    int getCounter();
}
