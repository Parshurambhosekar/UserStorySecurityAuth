package com.usertask.security.exception;

public class InvalidCredentialException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String message;
	
	public InvalidCredentialException(String message) {
		
		super(String.format(message));
	}
	

}
