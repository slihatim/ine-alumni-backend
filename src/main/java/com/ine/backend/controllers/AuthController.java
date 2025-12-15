package com.ine.backend.controllers;

import java.security.Principal;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.ine.backend.dto.SignInRequestDto;
import com.ine.backend.dto.SignInResponseDto;
import com.ine.backend.dto.SignUpRequestDto;
import com.ine.backend.exceptions.UserAlreadyExistsException;
import com.ine.backend.services.AuthService;
import com.ine.backend.services.EmailVerificationService;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/v1/auth")
@AllArgsConstructor
public class AuthController {
	private AuthService authService;
	private EmailVerificationService emailVerificationService;

	@PostMapping("/signup")
	public ResponseEntity<String> registerUser(@RequestBody @Valid SignUpRequestDto requestDto)
			throws UserAlreadyExistsException {

		authService.signUpUser(requestDto);

		// Directly after registering the user, we send the verification email
		emailVerificationService.sendVerificationToken(requestDto.getEmail());

		return ResponseEntity.status(HttpStatus.CREATED).body("User account successfully created!");
	}

	@PostMapping("/signin")
	public ResponseEntity<SignInResponseDto> signInUser(@RequestBody @Valid SignInRequestDto requestDto) {
		return ResponseEntity.ok(authService.signInUser(requestDto, false));
	}

	@GetMapping("/validate")
	public ResponseEntity<SignInResponseDto> validateAuthentication(Principal principal) {
		if (principal == null) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}
		return ResponseEntity.ok(authService.getAuthenticationState(principal.getName(), false));
	}
}
