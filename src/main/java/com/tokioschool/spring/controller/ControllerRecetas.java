package com.tokioschool.spring.controller;

import java.util.Set;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.tokioschool.spring.domain.Receta;
import com.tokioschool.spring.domain.dto.RecetaDTO;
import com.tokioschool.spring.service.RecetasService;
import com.tokioschool.spring.store.domain.dto.ImageResourceDTO;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor

@Tag(name ="Recipe",description = "SAVE NEW RECIPE, LOAD AND SAVE HIS IMAGE")
@RequestMapping("/recetas")
public class ControllerRecetas {
	
	private final RecetasService recetaService;
		
	@GetMapping({"recetas", "recetas/" })
	public ResponseEntity<Set<RecetaDTO>> getRecetas(){
		return ResponseEntity.ok(recetaService.getRecetas());
	}
	
	public ResponseEntity<RecetaDTO> newReceta(
			@RequestBody @Valid RecetaDTO recetaDTO,
			@RequestPart("imagen") @NotNull MultipartFile multipartFile ){
		
		
		String UUID = saveImage(multipartFile);
		
		Receta receta = Receta.builder()
				.name(recetaDTO.getName())
				.ingredients(recetaDTO.getIngredients())
				.price(recetaDTO.getPrice())
				.descripcion(recetaDTO.getDescripcion())
				.imagen(UUID)
				.build();
		
		RecetaDTO ResponseRecetaDTO = recetaService.save(receta); 
		
		return ResponseEntity.ok(ResponseRecetaDTO); 
			
	}
	
	@PostMapping(value = "add-recetas")
	public ResponseEntity<RecetaDTO> addReceta(@RequestBody @Valid RecetaDTO recetaDTO){
		
		Receta receta = Receta.builder()
				.name(recetaDTO.getName())
				.ingredients(recetaDTO.getIngredients())
				.principalIngredients(recetaDTO.getPrincipalIngredients())
				.price(recetaDTO.getPrice())
				.descripcion(recetaDTO.getDescripcion())
				.build();
		
		RecetaDTO ResponseRecetaDTO = recetaService.save(receta); 
		
		return ResponseEntity.ok(ResponseRecetaDTO); 
			
	}
	
	@PostMapping(value = "add-img/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<?> addImagen(
			@RequestPart("imagen") @NotNull MultipartFile multiPartFile, 
			@PathVariable("id") long id) throws IllegalAccessException{
		
		recetaService.findById(id); 
		
		String UUID = saveImage(multiPartFile); 
		recetaService.updateImage(id, UUID);	
		
		return ResponseEntity.ok().build();
	}
	
	private String saveImage(MultipartFile multiPartFile) {
		
		return recetaService.saveImage(multiPartFile, "imagenReceta")
				.get()
				.getResourceId().toString();
	}

	@GetMapping("get-img/{resourceId}")
	public ResponseEntity<byte[]> getImage(@PathVariable("resourceId") String UUID){
		
		ImageResourceDTO imageResourceDTO = recetaService.getImage(UUID);
		
		return ResponseEntity.ok()
				.contentType(MediaType.parseMediaType(imageResourceDTO.getContentType()))
				.contentLength(imageResourceDTO.getSize())
				.body(imageResourceDTO.getContent());
				
	}
}


