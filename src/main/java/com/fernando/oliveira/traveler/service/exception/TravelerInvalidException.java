package com.fernando.oliveira.traveler.service.exception;

public class TravelerInvalidException extends RuntimeException {


	private static final long serialVersionUID = 1L;
	
	public TravelerInvalidException (String message) {
		super(message);
	}
}
