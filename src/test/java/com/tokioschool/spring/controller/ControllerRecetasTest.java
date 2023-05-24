package com.tokioschool.spring.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tokioschool.spring.domain.Receta;
import com.tokioschool.spring.domain.dto.RecetaDTO;
import com.tokioschool.spring.security.JwtRequestFilter;
import com.tokioschool.spring.security.JwtTokenUtil;
import com.tokioschool.spring.service.RecetasService;

@WebMvcTest(ControllerRecetas.class)
public class ControllerRecetasTest {
    
    @MockBean RecetasService recetasService;
    @Autowired MockMvc mockMvc;
    @Autowired JwtRequestFilter jwtRequestFilter;
    @Autowired WebApplicationContext webApplicationContext;
    @Autowired ObjectMapper objectMapper;
    
    @MockBean
    private JwtTokenUtil jwtTokenUtil;

    RecetaDTO recetaDTO;
    Receta receta;

    @BeforeEach
        
    public void setup() {
        
        recetaDTO = RecetaDTO.builder()
        .name("spaguetis")
        .price(5.2f)
        .principalIngredients(new ArrayList<String>(
                List.of("tomate", "oregano","carne", "pasta", "salsas")))
        .imagen("spaguetis001")
        .descripcion("muy bueno")
        .build();

        receta = Receta.builder()
        .name("spaguetis")
        .price(5.2f).principalIngredients(new ArrayList<String>(
                List.of("tomate", "oregano","carne", "pasta", "salsas")))
        .imagen("spaguetis001")
        .descripcion("muy bueno")
        .build();
        
        mockMvc = MockMvcBuilders
                .webAppContextSetup(webApplicationContext)
                .addFilters(jwtRequestFilter)
                .build();
    }


    @Test
    void givenEndpointWhenGet_recetaThenOk() throws Exception{
        List<RecetaDTO> recetas = new ArrayList<>();
        recetas.add(0, recetaDTO);
        
        Mockito.when(recetasService.getRecetas()).thenReturn(new HashSet<>(recetas));
        
        mockMvc
            .perform(get("/recetas/recetas").contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$", Matchers.hasSize(1)))    
            .andExpect(jsonPath("$[0].name").value("spaguetis"));
        
            verify(recetasService, times(1)).getRecetas();
     }

    @Test
    @WithMockUser(username = "ferrer", password = "123456", roles = "USER", authorities = "USER")
    void givenEndPointWhenPost_recetaThenOk() throws Exception {
        // Datos de prueba
        RecetaDTO recetaDTO = new RecetaDTO();
        recetaDTO.setName("Receta de prueba");
        recetaDTO.setIngredients("Ingrediente 1, Ingrediente 2");
        recetaDTO.setPrice(10.0f);
        recetaDTO.setDescripcion("Descripción de la receta");

        RecetaDTO responseRecetaDTO = new RecetaDTO();
        responseRecetaDTO.setId(1L);
        responseRecetaDTO.setName("Receta de prueba");
        responseRecetaDTO.setIngredients("Ingrediente 1, Ingrediente 2");
        responseRecetaDTO.setPrice(10.0f);
        responseRecetaDTO.setDescripcion("Descripción de la receta");

        // Configuración del comportamiento del servicio mock
        when(recetasService.save(any(Receta.class))).thenReturn(recetaDTO);

        // Realizar la solicitud POST
        mockMvc.perform(post("/recetas/add-recetas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(recetaDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Receta de prueba"))
                .andExpect(jsonPath("$.ingredients").value("Ingrediente 1, Ingrediente 2"))
                .andExpect(jsonPath("$.price").value(10.0))
                .andExpect(jsonPath("$.descripcion").value("Descripción de la receta"));

        // Verificar que el método del servicio se haya llamado una vez
        verify(recetasService, times(1)).save(any(Receta.class));
    }

    
}


