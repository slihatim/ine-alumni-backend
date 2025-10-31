package com.ine.backend.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResetPasswordRequestDto {
	@Email(message = "L'adresse email n'est pas valide.")
	@NotBlank(message = "L'adresse email est obligatoire.")
	private String email;

	@NotBlank(message = "Le code de vérification est obligatoire.")
	private String token;

	@NotBlank(message = "Le nouveau mot de passe est obligatoire.")
	@Size(min = 8, message = "Le mot de passe doit contenir au moins 8 caractères.")
	@Size(max = 25, message = "Le mot de passe ne doit pas depasser 25 caractères.")
	private String newPassword;
}
