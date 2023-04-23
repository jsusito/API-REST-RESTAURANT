package com.tokioschool.spring.domain.dto;

import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;

import io.swagger.v3.oas.annotations.Hidden;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
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

public class GuestReservationDTO {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Hidden
	long id;
	
	@NotNull
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	LocalDate dateReservations;
	
	int numberPeople;
	
	int dinnerTable;
	
	String lunchHour;
	
	String username;

	String surname;

	String telephone;
	
	
}
