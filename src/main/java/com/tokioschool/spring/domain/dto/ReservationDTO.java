package com.tokioschool.spring.domain.dto;

import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class ReservationDTO {
	
		
	@NotNull
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate dateReservations;
	
	@NotNull 
	@Positive 
	@Min(1) 
	@Max(6) 
	@Digits(fraction = 0, integer = 1)
	private int numberPeople;
	
	private int lunchTable;
	
	private int dinnerTable;
	
	private String lunchHour;
	
	private String dinnerHour;
	
	private String userUsername;
}
