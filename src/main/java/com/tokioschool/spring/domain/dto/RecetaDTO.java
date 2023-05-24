package com.tokioschool.spring.domain.dto;

import java.util.List;

import org.modelmapper.ModelMapper;

import com.tokioschool.spring.domain.Receta;

import io.swagger.v3.oas.annotations.Hidden;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
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

public class RecetaDTO {
    
	@Hidden
	long id;
	
	@NotBlank
	String name;
	
	String imagen;
	
	String ingredients;

	List<String>  principalIngredients;

	
	@Positive
	@Max(40)
	float price;
	
	@NotBlank
	String descripcion;
	
		
	public static RecetaDTO convertDTO(Receta receta) {
		ModelMapper modelMapper = new ModelMapper();
		return modelMapper.map(receta, RecetaDTO.class);
		
	}
}
