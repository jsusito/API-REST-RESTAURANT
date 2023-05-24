package com.tokioschool.spring.security.config;

import java.security.SecureRandom;
import java.util.Base64;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

//Generamos un token aleatorio cada 20 minutos. Cogemos el de properties solo para inicializar

@Configuration
public class KeyGenerator {

   
    // @Value("${jwt.secret}")
    private static String key;

   @Bean
   public void taskSecurity(){
        ScheduledExecutorService executor;
        executor = Executors.newSingleThreadScheduledExecutor();
        executor.scheduleAtFixedRate(KeyGenerator::generateKey, 0, 5, TimeUnit.MINUTES);
   }
   
    public static void generateKey() {
        
        SecureRandom secureRandom = new SecureRandom();
        
        byte[] bytes = new byte[32];
        secureRandom.nextBytes(bytes);
        String newKey = Base64.getEncoder().encodeToString(bytes);
        key = newKey;
        
                
    }

    public static String getKeyJwtGenerator(){
        return key;
    }
  
}
