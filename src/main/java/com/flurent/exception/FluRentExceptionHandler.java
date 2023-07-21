package com.flurent.exception;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.ConversionNotSupportedException;
import org.springframework.beans.TypeMismatchException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.flurent.exception.message.ApiResponseError;

@ControllerAdvice
public class FluRentExceptionHandler extends ResponseEntityExceptionHandler {

	private ResponseEntity<Object> buildResponseEntity(ApiResponseError error) {
		return new ResponseEntity<>(error, error.getStatus());
	}

	@ExceptionHandler(ResourceNotFoundException.class)
	protected ResponseEntity<Object> handleResourceNotFoundException(ResourceNotFoundException exception,
			WebRequest request) {
		ApiResponseError error = new ApiResponseError(HttpStatus.NOT_FOUND, exception.getMessage(),
				request.getDescription(false));

		return buildResponseEntity(error);

	}

	@ExceptionHandler(ConflictException.class)
	protected ResponseEntity<Object> handleConflictException(ConflictException exception, WebRequest request) {
		ApiResponseError error = new ApiResponseError(HttpStatus.CONFLICT, exception.getMessage(),
				request.getDescription(false));

		return buildResponseEntity(error);

	}

	@ExceptionHandler(BadRequestException.class)
	protected ResponseEntity<Object> handleBadRequestException(BadRequestException exception, WebRequest request) {
		ApiResponseError error = new ApiResponseError(HttpStatus.BAD_REQUEST, exception.getMessage(),
				request.getDescription(false));

		return buildResponseEntity(error);

	}

	@ExceptionHandler(AccessDeniedException.class)
	protected ResponseEntity<Object> handleAccessDeniedException(AccessDeniedException exception, WebRequest request) {
		ApiResponseError error = new ApiResponseError(HttpStatus.FORBIDDEN, exception.getMessage(),
				request.getDescription(false));

		return buildResponseEntity(error);

	}

	@ExceptionHandler(AuthenticationException.class)
	protected ResponseEntity<Object> handleAuthenticationException(AuthenticationException exception,
			WebRequest request) {
		ApiResponseError error = new ApiResponseError(HttpStatus.BAD_REQUEST, exception.getMessage(),
				request.getDescription(false));

		return buildResponseEntity(error);

	}

	@ExceptionHandler(ImageFileException.class)
	protected ResponseEntity<Object> handleImageFileException(ImageFileException exception, WebRequest request) {
		ApiResponseError error = new ApiResponseError(HttpStatus.BAD_REQUEST, exception.getMessage(),
				request.getDescription(false));

		return buildResponseEntity(error);
	}

	@ExceptionHandler(ExcelReportException.class)
	protected ResponseEntity<Object> handleExcelReportException(ExcelReportException exception, WebRequest request) {
		ApiResponseError error = new ApiResponseError(HttpStatus.INTERNAL_SERVER_ERROR, exception.getMessage(),
				request.getDescription(false));

		return buildResponseEntity(error);

	}

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException exception,
			HttpHeaders headers, HttpStatus status, WebRequest request) {

		List<String> errors = exception.getBindingResult().getFieldErrors().stream().map(e -> e.getDefaultMessage())
				.collect(Collectors.toList());

		ApiResponseError error = new ApiResponseError(HttpStatus.BAD_REQUEST, errors.get(0),
				request.getDescription(false));

		return buildResponseEntity(error);

	}

	@Override
	protected ResponseEntity<Object> handleTypeMismatch(TypeMismatchException exception, HttpHeaders headers,
			HttpStatus status, WebRequest request) {
		ApiResponseError error = new ApiResponseError(HttpStatus.BAD_REQUEST, exception.getMessage(),
				request.getDescription(false));

		return buildResponseEntity(error);

	}

	@Override
	protected ResponseEntity<Object> handleConversionNotSupported(ConversionNotSupportedException exception,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		ApiResponseError error = new ApiResponseError(HttpStatus.INTERNAL_SERVER_ERROR, exception.getMessage(),
				request.getDescription(false));

		return buildResponseEntity(error);
	}

	@Override
	protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException exception,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		ApiResponseError error = new ApiResponseError(HttpStatus.BAD_REQUEST, exception.getMessage(),
				request.getDescription(false));

		return buildResponseEntity(error);

	}

	@ExceptionHandler(RuntimeException.class)
	protected ResponseEntity<Object> handleGeneralException(RuntimeException ex, WebRequest request) {
		ApiResponseError error = new ApiResponseError(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage(),
				request.getDescription(false));
		return buildResponseEntity(error);
	}

}
