package com.ine.backend.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AdminCreationRequestDto {
	@NotBlank(message = "Le nom complet ne doit pas être vide.")
	private String fullName;

	@Email(message = "L'adresse email n'est pas valide.")
	@NotBlank(message = "L'adresse email est obligatoire.")
	private String email;

	@NotBlank(message = "Le mot de passe est obligatoire.")
	@Size(min = 8, message = "Le mot de passe doit contenir au moins 8 caractères.")
	@Size(max = 25, message = "Le mot de passe ne doit pas depasser 25 caractères.")
	private String password;
}