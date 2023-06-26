package com.flurent.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.flurent.exception.message.ApiResponseError;

@ControllerAdvice
public class FluRentExceptionHandler extends ResponseEntityExceptionHandler {
	
	
	private ResponseEntity<Object> buildResponseEntity(ApiResponseError error){
		return new ResponseEntity<>(error, error.getStatus());
	}
	
	@ExceptionHandler(ResourceNotFoundException.class)
	protected ResponseEntity<Object>  handleResourceNotFoundException(ResourceNotFoundException exception, WebRequest request){
		ApiResponseError error = new ApiResponseError(HttpStatus.NOT_FOUND, exception.getMessage(), request.getDescription(false));
	
		return buildResponseEntity(error);
	
	}

	@ExceptionHandler(ConflictException.class)
	protected ResponseEntity<Object>  handleConflictException(ConflictException exception, WebRequest request){
		ApiResponseError error = new ApiResponseError(HttpStatus.CONFLICT, exception.getMessage(), request.getDescription(false));
	
		return buildResponseEntity(error);
	
	}

	@ExceptionHandler(BadRequestException.class)
	protected ResponseEntity<Object>  handleBadRequestException(BadRequestException exception, WebRequest request){
		ApiResponseError error = new ApiResponseError(HttpStatus.BAD_REQUEST, exception.getMessage(), request.getDescription(false));
	
		return buildResponseEntity(error);
	
	}


}
