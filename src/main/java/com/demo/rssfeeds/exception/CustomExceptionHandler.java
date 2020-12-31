package com.demo.rssfeeds.exception;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 * This class is a custom handler for our different "homemade" exceptions so that the HTTP response explains the failure.
 */
@ControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {
	@ExceptionHandler(InvalidRequestException.class)
	public final ResponseEntity<Object> handleUserNotFoundException(final InvalidRequestException ex, final WebRequest request) {
		List<String> details = new ArrayList<>();
		details.add(ex.getMessage());

		return new ResponseEntity<Object>("Invalid Request: " + ex.getMessage(), HttpStatus.BAD_REQUEST);
	}

}
