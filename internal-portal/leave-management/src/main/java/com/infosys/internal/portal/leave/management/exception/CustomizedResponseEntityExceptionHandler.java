package com.infosys.internal.portal.leave.management.exception;



import java.util.Date;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice 
@RestController 
public class CustomizedResponseEntityExceptionHandler extends ResponseEntityExceptionHandler{

	
	@ExceptionHandler(BadRequestException.class)// in vazul in care exceptia este UserNotFoundException, aruncam asta
	public final ResponseEntity<Object> handleUserNotFoundException(BadRequestException ex, WebRequest request) {
		
		if(ex.getErrors() == null) {
			return new ResponseEntity(ex.getMessage(), HttpStatus.BAD_REQUEST);
		}
		
		ExceptionResponse exceptionResponse = new ExceptionResponse(new Date(),
									ex.getMessage(),ex.getErrors()
									.getAllErrors()
									.stream()
									.map(x -> x.getDefaultMessage()).collect(Collectors.joining("; ")));
		return new ResponseEntity(exceptionResponse, HttpStatus.BAD_REQUEST);
	}
}
