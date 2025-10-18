package com.ine.backend.exceptions;

public class UnauthorizedProfileAccessException extends RuntimeException {
	public UnauthorizedProfileAccessException(String message) {
		super(message);
	}

}
