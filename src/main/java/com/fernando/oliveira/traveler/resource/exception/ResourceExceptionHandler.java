package com.fernando.oliveira.traveler.resource.exception;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.fernando.oliveira.traveler.service.exception.TravelerInvalidException;
import com.fernando.oliveira.traveler.service.exception.TravelerNotFoundException;

@ControllerAdvice
public class ResourceExceptionHandler extends ResponseEntityExceptionHandler {
	
	private static final String INVALID_FIELDS_MESSAGE = "Invalid field(s)";

	@ExceptionHandler(Exception.class)
	public final ResponseEntity<Object> handleAllExceptions(Exception ex, WebRequest request) {

		ExceptionResponse exceptionResponse = new ExceptionResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), ex.getMessage(),new Date(),
				 Arrays.asList(""));

		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(exceptionResponse);

	}

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {

		List<String> errors = new ArrayList<String>();
		
		ex.getBindingResult().getAllErrors().forEach(error -> {
			errors.add(error.getDefaultMessage());
		});
		
		ExceptionResponse exceptionResponse = new ExceptionResponse(HttpStatus.BAD_REQUEST.value(), INVALID_FIELDS_MESSAGE, new Date(), errors);
		
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exceptionResponse);
	}

	@ExceptionHandler(TravelerNotFoundException.class)
	public final ResponseEntity<Object> handleUserNotFoundExceptions(Exception ex, WebRequest request) {

		ExceptionResponse exceptionResponse = new ExceptionResponse(HttpStatus.NOT_FOUND.value(), ex.getMessage(),new Date(),
				 Arrays.asList(""));

		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exceptionResponse);

	}
	
	@ExceptionHandler(TravelerInvalidException.class)
	public final ResponseEntity<Object> handleTravelerInvalidExceptions(Exception ex, WebRequest request){
		
		ExceptionResponse exceptionResponse = new ExceptionResponse(HttpStatus.BAD_REQUEST.value(), ex.getMessage(),new Date(),
				 Arrays.asList(""));

		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exceptionResponse);
		
	}

	

}
