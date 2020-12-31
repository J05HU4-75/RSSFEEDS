package com.demo.rssfeeds.exception;

/**
 * A utilitary Exception for the REST Controller
 */

public class InvalidRequestException extends RuntimeException {
	public InvalidRequestException(final String exception) {
		super(exception);
	}
}
