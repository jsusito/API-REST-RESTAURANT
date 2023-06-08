package com.tokioschool.spring.integration;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nimbusds.jose.JOSEException;
import com.tokioschool.spring.controller.ControllerLogin;
import com.tokioschool.spring.controller.ControllerRecetas;
import com.tokioschool.spring.controller.ControllerReservation;
import com.tokioschool.spring.controller.ControllerUser;
import com.tokioschool.spring.domain.dto.UserDTO;
import com.tokioschool.spring.security.JwtRequest;
import com.tokioschool.spring.security.JwtTokenUtil;
import com.tokioschool.spring.service.UserService;

@SpringBootTest //Creamos todo el contexto de la aplicacion
@AutoConfigureMockMvc //No iniciamos el server con los puertos
public class AuthenticationIntegrationTest {
    
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    JwtTokenUtil jwtTokenUtil;

    @Autowired
    UserService userService;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    MockMvc  mockMvc;

    @Test
    public void testAuthentication() throws JOSEException {
       

        // Formamos este objeto  para autenticar al usuario
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken("ferrer", "123456");

        // Autenticar al usuario utilizando el AuthenticationManager
        Authentication authentication = authenticationManager.authenticate(authenticationToken);

        // Verificar si la autenticación fue exitosa
        assertTrue(authentication.isAuthenticated());
    }

    @Autowired ControllerLogin controllerLogin;
    @Autowired ControllerRecetas controllerRecetas;
    @Autowired ControllerReservation controllerReservation;
    @Autowired  ControllerUser controllerUser;
    
    @Test
    public void testControllers(){
        
        //Comprobamos que se crea el contexto de los controladores    
        Assertions.assertThat(controllerRecetas).isNotNull();
        Assertions.assertThat(controllerReservation).isNotNull();
        Assertions.assertThat(controllerUser).isNotNull();
        Assertions.assertThat(controllerLogin).isNotNull();
        
    }

    
    //Comprobar el funcionamiento del Login
    @Test
    void shouldReturnOkWhenGetToken() throws Exception {
        JwtRequest jwtRequest = new JwtRequest("ferrer", "123456");
        
        mockMvc.perform(post("/login")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(jwtRequest)))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").exists());
              
    };            
    
    //crear un nuevo usuario con role USER por defecto 
    //usamos el token en esta ocasion 
    @Test
    void givenNewUserWhenIsAuthenticateAdminThenOk() throws JsonProcessingException, Exception{
        
        int totalUser = userService.totalUsers();
         String token = jwtTokenUtil.generateToken("ferrer"); 
        
        UserDTO userRequest = UserDTO.builder()
            .name("jes234")
            .surname("3434ferrer")
            .birtDate(LocalDate.now().minusDays(10))
            .telephone("34584753456")
            .email("jsusito45634@gmail.com")
            .username("je343susFerrer")
            .password("1234534346")
            .build();
        
        String response = mockMvc.perform(post("/user/new-user")
                .header("Authorization", "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userRequest)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id").exists())
            .andExpect(jsonPath("$.active").value(true))
            .andReturn().getResponse().getContentAsString();

        Assertions.assertThat(userService.totalUsers()).isEqualTo(totalUser + 1);
        
        Map<String, Object> responseMap = objectMapper.readValue(response, Map.class);
        
        
        List<String> roles = (List<String>)responseMap.get("roles");
        try{
            roles.stream()
                .filter((role) -> 
                    role.equals("USER"))
                    .findAny()
                    .orElseThrow(() -> new RuntimeException("No se encontro el role USER"));
        
        }finally{
            userService.deleteUser((Integer) responseMap.get("id"));
        }
        
        
        
        Assertions.assertThat(totalUser).isEqualTo(userService.totalUsers());

    }

    
    @Test
    @WithMockUser(username = "xxxx", password = "xxxx", authorities = "ADMIN")
    void givenNewUserWhenExistThenOk() throws JsonProcessingException, Exception{
        
        UserDTO userRequest = UserDTO.builder()
            .name("jesus")
            .surname("ferrer")
            .birtDate(LocalDate.now().minusDays(10))
            .email("jsusito@gmail.com")
            .username("jesusFerrer")
            .telephone("345634563")
            .password("123456")
            .build();
        
       
            mockMvc
                .perform(post("/user/new-user")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(userRequest)))
                .andExpect(status().is4xxClientError());
    
            System.out.println("El usuario ya existe");
        
    }
}
                


            
    


