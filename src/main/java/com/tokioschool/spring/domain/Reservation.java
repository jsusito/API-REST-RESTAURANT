package com.tokioschool.spring.domain;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
@Entity(name = "reservations")

public class Reservation {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	long id;
	
	LocalDate dateReservations;
	
	int numberPeople;
	
	int lunchTable;
	
	int dinnerTable;
	
	String lunchHour;
	
	String dinnerHour;
	
	@ManyToOne
	@JoinColumn(name = "user_id")
	User user;
}
