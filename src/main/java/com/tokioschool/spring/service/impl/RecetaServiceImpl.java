package com.tokioschool.spring.service.impl;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.tokioschool.spring.domain.Receta;
import com.tokioschool.spring.domain.dto.RecetaDTO;
import com.tokioschool.spring.domain.repository.RecetasDAO;
import com.tokioschool.spring.service.RecetasService;
import com.tokioschool.spring.store.domain.dto.ImageResourceDTO;
import com.tokioschool.spring.store.domain.dto.ResourceIdDTO;
import com.tokioschool.spring.store.service.StoreService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class RecetaServiceImpl implements RecetasService {

	final private RecetasDAO recetaDAO;
	final private StoreService storeService;
	final private ModelMapper modelMapper;
	
	@Override
	public RecetaDTO save(Receta receta) {
		Receta recetaResponse = recetaDAO.save(receta);
		
		return modelMapper.map(recetaResponse, RecetaDTO.class);
		
		
		
	}

	@Override
	public RecetaDTO getReceta(long id) {
		
		RecetaDTO recetaDTO = new RecetaDTO();
		
		//Si id invalido devuelve una en blanco
		return recetaDAO.findById(id)
				.map(RecetaDTO::convertDTO).orElse(recetaDTO);
	}

	@Override
	public Set<RecetaDTO> getRecetas() {
		
		return new HashSet<RecetaDTO>(
				recetaDAO
					.findAll()
					.stream()
					.map(RecetaDTO::convertDTO)
					.toList());
	}
	
	

	@Override
	public Optional<ResourceIdDTO> saveImage(MultipartFile multiPartFile, String descripcion) {
		return storeService.save(multiPartFile, descripcion);
	}

	@Override
	public int updateImage(long id, String UUID) {
		
		return recetaDAO.updateImage(id, UUID);
	}

	@Override
	public ImageResourceDTO getImage(String resourceId) {
		
		return storeService.find(UUID.fromString(resourceId))
				.orElseThrow(() -> 
					new IllegalArgumentException("Resource widht id:%s not found o not available".formatted(resourceId)));
				
	}

	@Override
	public RecetaDTO findById(long id) {
		Optional<Receta> receta = recetaDAO.findById(id);
		return receta.map(RecetaDTO::convertDTO).orElseThrow();
		
	}
	
	
}
