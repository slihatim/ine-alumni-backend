package com.ine.backend.exceptions;

public class OfferNotFoundException extends RuntimeException {
	public OfferNotFoundException(Long id) {
		super("Offer with id " + id + " not found");
	}
}
