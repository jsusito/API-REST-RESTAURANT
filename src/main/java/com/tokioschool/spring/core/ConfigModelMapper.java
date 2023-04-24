package com.tokioschool.spring.core;

import java.util.List;
import java.util.Set;

import org.modelmapper.AbstractConverter;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.tokioschool.spring.domain.Role;
import com.tokioschool.spring.domain.User;
import com.tokioschool.spring.domain.dto.GuestReservationDTO;
import com.tokioschool.spring.domain.dto.ReservationUserAndGuest;
import com.tokioschool.spring.domain.dto.UserDTO;

@Configuration
public class ConfigModelMapper {

	@Bean
	public ModelMapper modelMapper() {

		ModelMapper modelMapper = new ModelMapper();

		//Configuramos para las clasesDTO
		PropertyMap<GuestReservationDTO,ReservationUserAndGuest> mapReservation = new PropertyMap<GuestReservationDTO,ReservationUserAndGuest>() {

			@Override
			protected void configure() {
				map().setUserUsername(source.getUsername());
				map().setUserTelephone(source.getTelephone());
			}
		};
		
		//Conversor para unir la consulta en la implementacion de ReservationUserAndGuestServImpl
		modelMapper.typeMap(GuestReservationDTO.class, ReservationUserAndGuest.class)
			.addMappings(mapReservation);
		
		
		// Configuramos para que no mande la contraseña
		modelMapper.typeMap(User.class, UserDTO.class)
			.addMappings(mapper -> mapper.skip(UserDTO::setPassword)) //no haga mapeo de pasword
			.addMappings(new PropertyMap<>() { //mapea las autoridades
					@Override
					protected void configure() {
						Converter<Set<Role>, List<String>> converter = new AbstractConverter<>() {

							@Override
							protected List<String> convert(Set<Role> source) {
								return source.stream().map(Role::getName).toList();
							}
						};
						using(converter).map(source.getRoles(), destination.getRoles());
					}
				});

		return modelMapper;
	}
}
