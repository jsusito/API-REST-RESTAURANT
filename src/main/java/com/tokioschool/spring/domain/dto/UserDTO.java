package com.tokioschool.spring.domain.dto;

import java.time.LocalDate;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
	
	@Hidden
	private long id;
	
	@NotNull
	private String username;
    
	@NotNull
	private String name;
    
    
    @NotNull
    @Schema(description = "Only register new User, This field don't send in a request")
    private String password;
    
    private String surname;
    
    @NotNull
    private String email;
    
    private String telephone;
    
    private String address;
    
    private String city;
    private String postalCode;
    
    private String province;
    
    private String country;
    
    private String image;
    
    private boolean active;
    
    private int age;
    
    private List<String> roles;
    
        
    @NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    LocalDate birtDate;

    
    
    

}
