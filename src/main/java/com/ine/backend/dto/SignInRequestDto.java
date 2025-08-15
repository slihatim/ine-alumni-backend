package com.ine.backend.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SignInRequestDto {
	@Email(message = "L'adresse email n'est pas valide.")
	@NotBlank(message = "L'adresse email est obligatoire.")
	private String email;

	@NotBlank(message = "Le mot de passe est obligatoire.")
	private String password;
}
