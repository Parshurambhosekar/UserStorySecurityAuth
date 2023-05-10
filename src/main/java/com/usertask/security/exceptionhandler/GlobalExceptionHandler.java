package com.usertask.security.exceptionhandler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.usertask.security.binding.ApiResponse;
import com.usertask.security.exception.InvalidCredentialException;

@RestControllerAdvice
public class GlobalExceptionHandler {
	
	@ExceptionHandler(InvalidCredentialException.class)
	public ResponseEntity<ApiResponse> handelInvalidCredentialException(InvalidCredentialException exception){
		
		String message = exception.getMessage();
		
		ApiResponse response=new ApiResponse(message);
	
		return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
		
	}
	
	

}
