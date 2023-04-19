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
import com.tokioschool.spring.domain.dto.UserDTO;

@Configuration
public class ConfigModelMapper {

	@Bean
	public ModelMapper modelMapper() {

		ModelMapper modelMapper = new ModelMapper();

		// Configuramos para que no mande la contraseña
		modelMapper.typeMap(User.class, UserDTO.class)
			.addMappings(mapper -> mapper.skip(UserDTO::setPassword)) //no haga mapeo de pasword
			.addMappings(new PropertyMap<>() {
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
