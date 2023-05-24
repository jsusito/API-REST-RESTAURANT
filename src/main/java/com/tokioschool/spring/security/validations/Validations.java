package com.tokioschool.spring.security.validations;

import java.util.NoSuchElementException;

import org.springframework.security.core.context.SecurityContextHolder;


public class Validations {
    
   
    public static void checkUser(String username){
        String userContext = SecurityContextHolder.getContext().getAuthentication().getName(); 
            if(!username.equals(userContext))
                throw new NoSuchElementException("error usuario"); 
    }

}
