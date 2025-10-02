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

	@Size(min = 2, max = 100, message = "Le nom complet doit contenir entre 2 et 100 caractères.")
	private String fullName;

	private Major major;

	@Min(value = 1961, message = "L'année de promo doit être supérieure ou égale à 1961.")
	@Max(value = 2200, message = "L'année de promo doit être inférieure ou égale à 2200.")
	private Integer graduationYear;

	@Pattern(regexp = "^[+]?[0-9\\s-()]{8,15}$", message = "Le numéro de téléphone n'est pas valide.")
	private String phoneNumber;

	private LocalDate birthDate;

	private Gender gender;

	@Size(max = 50, message = "Le nom du pays ne doit pas dépasser 50 caractères.")
	private String country;

	@Size(max = 50, message = "Le nom de la ville ne doit pas dépasser 50 caractères.")
	private String city;

	@Pattern(regexp = "^https://[a-z]{2,3}\\.linkedin\\.com/.*", message = "Le lien LinkedIn n'est pas valide.")
	private String linkedinId;
}
