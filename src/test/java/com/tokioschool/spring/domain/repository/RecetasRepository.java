package com.tokioschool.spring.domain.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.tokioschool.spring.domain.Receta;


@DataJpaTest
class RecetasRepository {
    
    
    @Autowired RecetasDAO recetasDAO;
    
    
    @BeforeEach
    void before(){
        
   

        Receta spaguetis = Receta.builder()
            .name("spaguetis")
            .price(5.2f).principalIngredients(new ArrayList<String>(
                    List.of("tomate", "oregano","carne", "pasta", "salsas")))
            .imagen("spaguetis001")
            .build();

            Receta flan = Receta.builder()
            .name("flan de huevo")
            .price(5.6f).principalIngredients(new ArrayList<String>(
                    List.of("huevo", "leche","azucar", "leche condensada", "caramelo")))
            .imagen("flan")
            .build();

        recetasDAO.saveAll(List.of(spaguetis, flan));

    }
   
    @Test
    void givenRecetaWhenFindThenOk(){
        List<Receta> recetas = recetasDAO.findAll();
        
        assertThat(recetas.size() == 2 );
        assertThat(recetas.stream()
            .filter((receta) -> 
                receta.getName().equals("spaguetis")).findAny().get())
            .returns("spaguetis", Receta::getName)
            .returns(5.2f, Receta::getPrice)
            .returns("tomate", receta -> 
                receta.getPrincipalIngredients()
                    .stream()
                    .filter((ingredientes) -> ingredientes.equals("tomate")).findAny().get());
    }

}
