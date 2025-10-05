package com.ine.backend.utils;

import java.util.regex.Pattern;

import org.springframework.stereotype.Component;

/**
 * Utility class for profile-related validations and operations
 */
@Component
public class ProfileValidationUtils {

	// LinkedIn URL pattern - matches various LinkedIn URL formats
	private static final Pattern LINKEDIN_URL_PATTERN = Pattern.compile(
			"^https://([a-z]{2,3}\\.)?linkedin\\.com/(in/[a-zA-Z0-9-_]+/?|pub/[a-zA-Z0-9-_]+/[a-zA-Z0-9]+/[a-zA-Z0-9]+/[a-zA-Z0-9]+/?)$");

	// Phone number pattern - international format
	private static final Pattern PHONE_PATTERN = Pattern.compile("^[+]?[1-9]\\d{0,3}[0-9\\s-()]{6,14}$");

	// Name pattern - allows letters, spaces, hyphens, apostrophes, and common
	// diacritics
	private static final Pattern NAME_PATTERN = Pattern.compile("^[\\p{L}\\s'.-]+$");

	/**
	 * Validates LinkedIn URL format
	 *
	 * @param linkedinUrl
	 *            The LinkedIn URL to validate
	 * @return true if valid, false otherwise
	 */
	public boolean isValidLinkedInUrl(String linkedinUrl) {
		if (linkedinUrl == null || linkedinUrl.trim().isEmpty()) {
			return true; // LinkedIn URL is optional
		}
		return LINKEDIN_URL_PATTERN.matcher(linkedinUrl.trim()).matches();
	}

	/**
	 * Validates phone number format
	 *
	 * @param phoneNumber
	 *            The phone number to validate
	 * @return true if valid, false otherwise
	 */
	public boolean isValidPhoneNumber(String phoneNumber) {
		if (phoneNumber == null || phoneNumber.trim().isEmpty()) {
			return true; // Phone number is optional
		}
		return PHONE_PATTERN.matcher(phoneNumber.trim()).matches();
	}

	/**
	 * Validates full name format
	 *
	 * @param fullName
	 *            The full name to validate
	 * @return true if valid, false otherwise
	 */
	public boolean isValidFullName(String fullName) {
		if (fullName == null || fullName.trim().isEmpty()) {
			return false; // Full name is required
		}
		return NAME_PATTERN.matcher(fullName.trim()).matches();
	}

	/**
	 * Sanitizes a string by trimming whitespace and removing extra spaces
	 *
	 * @param input
	 *            The input string
	 * @return Sanitized string or null if input is null
	 */
	public String sanitizeString(String input) {
		if (input == null) {
			return null;
		}
		return input.trim().replaceAll("\\s+", " ");
	}

	/**
	 * Validates graduation year range
	 *
	 * @param graduationYear
	 *            The graduation year to validate
	 * @return true if valid, false otherwise
	 */
	public boolean isValidGraduationYear(Integer graduationYear) {
		if (graduationYear == null) {
			return false; // Graduation year is required
		}
		int currentYear = java.time.Year.now().getValue();
		return graduationYear >= 1961 && graduationYear <= (currentYear + 10); // Allow future graduations up to 10
																				// years
	}

	/**
	 * Masks sensitive information in logs (e.g., email, phone)
	 *
	 * @param sensitive
	 *            The sensitive string to mask
	 * @return Masked string for logging
	 */
	public String maskSensitiveInfo(String sensitive) {
		if (sensitive == null || sensitive.length() <= 4) {
			return "****";
		}

		if (sensitive.contains("@")) {
			// Email masking
			String[] parts = sensitive.split("@");
			String localPart = parts[0];
			String domain = parts[1];

			if (localPart.length() <= 2) {
				return "**@" + domain;
			}
			return localPart.substring(0, 2) + "***@" + domain;
		} else {
			// Phone or other sensitive data masking
			return sensitive.substring(0, 2) + "*".repeat(sensitive.length() - 4)
					+ sensitive.substring(sensitive.length() - 2);
		}
	}

	/**
	 * Checks if a string contains potentially dangerous content
	 *
	 * @param input
	 *            The input to check
	 * @return true if safe, false if potentially dangerous
	 */
	public boolean isSafeInput(String input) {
		if (input == null) {
			return true;
		}

		String lowerInput = input.toLowerCase();
		String[] dangerousPatterns = {"<script", "javascript:", "onclick=", "onerror=", "onload=", "eval(", "alert(",
				"document.cookie", "window.location"};

		for (String pattern : dangerousPatterns) {
			if (lowerInput.contains(pattern)) {
				return false;
			}
		}

		return true;
	}
}
