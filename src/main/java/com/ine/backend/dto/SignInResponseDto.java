package com.ine.backend.dto;

import com.ine.backend.entities.Role;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SignInResponseDto {
	private String fullName;
	private String email;
	private String token;
	private String type;
	private Role role;
	private Boolean isEmailVerified;
	private Boolean isAccountVerified;
}
