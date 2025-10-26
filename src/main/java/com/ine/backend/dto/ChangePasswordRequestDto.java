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

	@NotBlank(message = "Current password is required.")
	private String currentPassword;

	@NotBlank(message = "New password is required.")
	@Size(min = 8, message = "New password must be at least 8 characters long.")
	private String newPassword;

	@NotBlank(message = "Password confirmation is required.")
	private String confirmPassword;
}
