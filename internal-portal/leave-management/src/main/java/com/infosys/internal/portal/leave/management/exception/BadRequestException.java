package com.infosys.internal.portal.leave.management.exception;


import org.springframework.validation.Errors;
public class BadRequestException  extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Errors errors;
	
	public BadRequestException(String message, Errors errors) {
		super(message);
		this.errors = errors;
	}
	
	public BadRequestException(String message) {
		super(message);
	}
	
	public Errors getErrors() {
		return errors;
	}
	public void setErrors(Errors errors) {
		this.errors = errors;
	}
	
	
}
