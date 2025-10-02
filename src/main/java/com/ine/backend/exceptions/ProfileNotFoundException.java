package com.ine.backend.exceptions;

public class ProfileNotFoundException extends RuntimeException {
	public ProfileNotFoundException(String message) {
		super(message);
	}

	public ProfileNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}
}
