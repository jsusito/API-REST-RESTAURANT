package com.tokioschool.spring.exception;

import java.util.Map;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import jakarta.validation.ConstraintViolationException;

@RestControllerAdvice
public class HandleException {

	@ResponseStatus(HttpStatus.BAD_REQUEST) 
	@ExceptionHandler(ConstraintViolationException.class)
	public Map<String, String> handleConstraintViolationException(ConstraintViolationException e) { 
		return Map.of( "message", e.getMessage());
	}
	
	
	@ResponseStatus(HttpStatus.NOT_FOUND) 
	@ExceptionHandler(NoSuchElementException.class)
	public Map<String, String> handleNotFoundException(NoSuchElementException e) { 
		return Map.of( "message", e.getMessage());
	}
	
	@ResponseStatus(HttpStatus.BAD_REQUEST) 
	@ExceptionHandler (MethodArgumentNotValidException.class) 
	public Map<String, String> methodArgumentNotValidException(MethodArgumentNotValidException e) { 
		
		String message = e.getBindingResult().getFieldErrors().stream()
				.map(error -> error.getField() + ": " + error.getDefaultMessage())
				.collect(Collectors.joining(". ")); 
		return Map.of("message", message);
	}
	
	@ResponseStatus (HttpStatus.BAD_REQUEST) //Para los json mal formados
	@ExceptionHandler (HttpMessageNotReadableException.class) 
	public Map<String, String> httpMessageNotReadobleException(HttpMessageNotReadableException e) { 
		return Map.of("message", "malformed request");
	}

	@ResponseStatus(HttpStatus.EXPECTATION_FAILED) 
	@ExceptionHandler(IllegalArgumentException.class)
	public Map<String, String> noFoundField(IllegalArgumentException e) { 
		
		return Map.of( "message", e.getMessage());
	}
	
}
