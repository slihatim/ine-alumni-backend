package com.ine.backend.exceptions;

public class EmailAlreadyVerifiedException extends RuntimeException {
	public EmailAlreadyVerifiedException(String message) {
		super(message);
	}
}
