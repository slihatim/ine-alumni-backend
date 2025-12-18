package com.ine.backend.controllers;

import java.security.Principal;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.ine.backend.dto.SignInRequestDto;
import com.ine.backend.dto.SignInResponseDto;
import com.ine.backend.services.AuthService;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/v1/auth/admin")
@AllArgsConstructor
public class AdminAuthController {
	private AuthService authService;

	@PostMapping("/signin")
	public ResponseEntity<SignInResponseDto> signInAdmin(@RequestBody @Valid SignInRequestDto requestDto) {
		SignInResponseDto response = authService.signInUser(requestDto, true);
		return ResponseEntity.ok(response);
	}

	@GetMapping("/validate")
	public ResponseEntity<SignInResponseDto> validateAuthentication(Principal principal) {
		if (principal == null) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}
		return ResponseEntity.ok(authService.getAuthenticationState(principal.getName(), true));
	}
}
