package com.tokioschool.spring.domain;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
@Entity(name = "guestReservation")

public class GuestReservation {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	long id;
	
	LocalDate dateReservations;
	
	int numberPeople;
	
	int dinnerTable;
	
	String lunchHour;
	
	String username;

	String surname;

	String telephone;
	
	
}
