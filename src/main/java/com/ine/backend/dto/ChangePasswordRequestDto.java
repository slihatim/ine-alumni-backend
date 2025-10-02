package com.ine.backend.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChangePasswordRequestDto {

	@NotBlank(message = "Le mot de passe actuel est obligatoire.")
	private String currentPassword;

	@NotBlank(message = "Le nouveau mot de passe est obligatoire.")
	@Size(min = 8, message = "Le nouveau mot de passe doit contenir au moins 8 caractères.")
	private String newPassword;

	@NotBlank(message = "La confirmation du mot de passe est obligatoire.")
	private String confirmPassword;
}
