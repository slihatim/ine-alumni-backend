package com.ine.backend.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChangeEmailRequestDto {

	@Email(message = "L'adresse email n'est pas valide.")
	@NotBlank(message = "L'adresse email est obligatoire.")
	private String newEmail;
}
