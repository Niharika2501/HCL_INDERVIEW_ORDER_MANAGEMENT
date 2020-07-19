package com.order.exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import lombok.extern.slf4j.Slf4j;

@RestControllerAdvice
@Slf4j
public class OrderServiceExceptionHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler(Exception.class)
	public final ResponseEntity<Object> handleAllExceptions(Exception ex) {
		log.error("handleAllExceptions exception {}",ex.getMessage());
		return getObjectResponseEntity(ex.getLocalizedMessage(), "Server Error", HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ExceptionHandler(OrderNotFoundException.class)
	public final ResponseEntity<Object> handleOrderNotFoundException(OrderNotFoundException ex) {
		log.error("handleOrderNotFoundException exception {}",ex.getMessage());
		return getObjectResponseEntity(ex.getLocalizedMessage(), ex.getMessage(), HttpStatus.NOT_FOUND);
	}

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
		log.error("handleMethodArgumentNotValid exception {}",ex.getMessage());
		String details = null;
		for(ObjectError error : ex.getBindingResult().getAllErrors()) {
			details = error.getDefaultMessage();
		}
		return getObjectResponseEntity(details, "Validation Failed", HttpStatus.BAD_REQUEST);
	}

	private ResponseEntity<Object> getObjectResponseEntity(String msg, Object details, HttpStatus httpStatus) {
		ErrorResponse error = new ErrorResponse(msg, details, httpStatus.value());
		return new ResponseEntity<Object>(error, httpStatus);
	}
}