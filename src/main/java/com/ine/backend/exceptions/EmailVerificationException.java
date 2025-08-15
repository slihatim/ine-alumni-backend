package com.ine.backend.exceptions;

import org.springframework.security.core.AuthenticationException;

public class EmailVerificationException extends AuthenticationException {
	public EmailVerificationException(String message) {
		super(message);
	}
}
