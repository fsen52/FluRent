package com.flurent.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

 //@ResponseStatus(HttpStatus.NOT_FOUND) /* I created self Exception Handler*/
public class ResourceNotFoundException extends RuntimeException {

	public ResourceNotFoundException(String message) {

		super(message);
	
	}
}
