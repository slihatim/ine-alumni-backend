package com.ine.backend.dto;

import java.time.LocalDate;

import com.ine.backend.entities.Gender;
import com.ine.backend.entities.Major;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SignUpRequestDto {
	@NotBlank(message = "Full name is required.")
	private String fullName;

	@Email(message = "Email address is not valid.")
	@NotBlank(message = "Email address is required.")
	private String email;

	@NotBlank(message = "Password is required.")
	@Size(min = 8, message = "Password must be at least 8 characters long.")
	@Size(max = 25, message = "Password must not exceed 25 characters.")
	private String password;

	@NotNull(message = "Major is required.")
	private Major major;

	@NotNull(message = "Graduation year is required.")
	@Min(value = 1961, message = "Graduation year must be greater than or equal to 1961.")
	@Max(value = 2200, message = "Graduation year must be less than or equal to 2200.")
	private Integer graduationYear;

	private String phoneNumber;
	private LocalDate birthDate;
	private Gender gender;
	private String country;
	private String city;
}
