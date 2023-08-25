package com.flurent.exception;

 //@ResponseStatus(HttpStatus.NOT_FOUND) /* I created self Exception Handler*/
public class ResourceNotFoundException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ResourceNotFoundException(String message) {

		super(message);
	
	}
}
