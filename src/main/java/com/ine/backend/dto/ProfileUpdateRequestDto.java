package com.ine.backend.dto;

import java.time.LocalDate;

import com.ine.backend.entities.Gender;
import com.ine.backend.entities.Major;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
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

	@Min(value = 1961, message = "Graduation year must be greater than or equal to 1961.")
	@Max(value = 2200, message = "Graduation year must be less than or equal to 2200.")
	private Integer graduationYear;

	@Pattern(regexp = "^[+]?[0-9\\s-()]{8,15}$", message = "Phone number is not valid.")
	private String phoneNumber;

	private LocalDate birthDate;

	private Gender gender;

	@Size(max = 50, message = "Country name must not exceed 50 characters.")
	private String country;

	@Size(max = 50, message = "City name must not exceed 50 characters.")
	private String city;

	@Pattern(regexp = "^https://[a-z]{2,3}\\.linkedin\\.com/.*", message = "LinkedIn link is not valid.")
	private String linkedinId;
}
