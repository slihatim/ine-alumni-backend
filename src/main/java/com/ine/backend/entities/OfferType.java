package com.ine.backend.entities;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import lombok.Getter;

/**
 * Enum representing different types of job offers. Replaces the previous
 * OfferType entity for better type safety.
 *
 * @author TahaBENMALEK
 * @version 1.0
 */
// FIXED: Moved enum to entities package instead of creating new enums package
@Getter
public enum OfferType {
	JOB("job"), INTERNSHIP("internship"), ALTERNANCE("alternance"), BENEVOLAT("benevolat"), OTHER("other");

	private final String value;

	OfferType(String value) {
		this.value = value;
	}

	@JsonCreator
	public static OfferType fromJson(String value) {
		return fromString(value);
	}

	@JsonValue
	public String toJson() {
		return this.value;
	}

	/**
	 * Converts string value to OfferType enum. Case-insensitive conversion.
	 *
	 * @param value
	 *            string value to convert
	 * @return OfferType enum
	 * @throws IllegalArgumentException
	 *             if value doesn't match any enum
	 */
	public static OfferType fromString(String value) {
		if (value == null) {
			throw new IllegalArgumentException("Offer type cannot be null");
		}

		for (OfferType type : OfferType.values()) {
			if (type.value.equalsIgnoreCase(value)) {
				return type;
			}
		}

		throw new IllegalArgumentException(
				"Invalid offer type: " + value + ". Valid types are: job, internship, alternance, benevolat, other");
	}

	/**
	 * Checks if the offer type requires a custom type specification.
	 *
	 * @return true if this is OTHER type, false otherwise
	 */
	public boolean requiresCustomType() {
		return this == OTHER;
	}
}
