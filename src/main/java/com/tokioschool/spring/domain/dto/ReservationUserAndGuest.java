package com.tokioschool.spring.domain.dto;

import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class ReservationUserAndGuest {
	
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	LocalDate dateReservations;
	
	int numberPeople;
	
	int dinnerTable;
	
	String lunchHour;
	
	String userUsername;

	String userTelephone;
	
	
}
