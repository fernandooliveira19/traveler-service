package com.fernando.oliveira.traveler.service.exception;

public class TravelerNotFoundException extends RuntimeException {


	private static final long serialVersionUID = 1L;
	
	public TravelerNotFoundException (String message) {
		super(message);
	}
}