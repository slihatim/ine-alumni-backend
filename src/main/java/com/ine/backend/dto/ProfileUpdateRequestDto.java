package com.ine.backend.dto;

import java.time.LocalDate;

import com.ine.backend.entities.Major;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProfileUpdateRequestDto {

	@Size(min = 2, max = 100, message = "Full name must be between 2 and 100 characters.")
	private String fullName;

	private Major major;

	@Pattern(regexp = "^[+]?[1-9]\\d{0,3}[0-9\\s\\-()]{6,14}$", message = "Phone number is not valid.")
	private String phoneNumber;

	private LocalDate birthDate;
	@Size(min = 4, max = 50, message = "Country name must not exceed 50 characters.")
	private String country;

	@Size(min = 4, max = 50, message = "City name must not exceed 50 characters.")
	private String city;

	@Pattern(regexp = "^https://([a-z]{2,3}\\.)?linkedin\\.com/(in/[a-zA-Z0-9-_]+/?|pub/[a-zA-Z0-9-_]+/[a-zA-Z0-9]+/[a-zA-Z0-9]+/[a-zA-Z0-9]+/?)$", message = "LinkedIn link is not valid.")
	private String linkedinId;
}
