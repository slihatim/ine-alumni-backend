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
	@NotBlank(message = "Le nom complet ne doit pas être vide.")
	private String fullName;

	@Email(message = "L'adresse email n'est pas valide.")
	@NotBlank(message = "L'adresse email est obligatoire.")
	private String email;

	@NotBlank(message = "Le mot de passe est obligatoire.")
	@Size(min = 8, message = "Le mot de passe doit contenir au moins 8 caractères.")
	@Size(max = 25, message = "Le mot de passe ne doit pas depasser 25 caractères.")
	private String password;

	@NotNull(message = "La filière est obligatoire.")
	private Major major;

	@NotNull(message = "L'année de votre promotion est obligatoire.")
	@Min(value = 1961, message = "L'année de promo doit être supérieure ou égale à 1961.")
	@Max(value = 2200, message = "L'année de promo doit être inférieure ou égale à 2200.")
	private Integer graduationYear;

	private String phoneNumber;
	private LocalDate birthDate;
	private Gender gender;
	private String country;
	private String city;
}
