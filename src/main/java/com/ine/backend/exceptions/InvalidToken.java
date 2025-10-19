package com.ine.backend.exceptions;

public class InvalidToken extends RuntimeException {
	public InvalidToken(String message) {
		super(message);
	}
}
