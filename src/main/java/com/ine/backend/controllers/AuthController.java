package com.ine.backend.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.ine.backend.dto.ApiResponseDto;
import com.ine.backend.dto.SignInRequestDto;
import com.ine.backend.dto.SignInResponseDto;
import com.ine.backend.dto.SignUpRequestDto;
import com.ine.backend.exceptions.UserAlreadyExistsException;
import com.ine.backend.security.EmailVerificationService;
import com.ine.backend.services.AuthService;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/v1/auth")
@AllArgsConstructor
public class AuthController {
  private AuthService authService;
  private EmailVerificationService emailVerificationService;

	@PostMapping("/signup")
	public ResponseEntity<ApiResponseDto<String>> registerUser(@RequestBody @Valid SignUpRequestDto requestDto)
			throws UserAlreadyExistsException {

    authService.signUpUser(requestDto);

    // Directly after registering the user, we send the verification email
    emailVerificationService.sendVerificationToken(requestDto.getEmail());

    return ResponseEntity.status(HttpStatus.CREATED)
        .body(new ApiResponseDto<>("Le compte utilisateur a été créé avec succès !", null, true));
  }

	@PostMapping("/signin")
	public ResponseEntity<ApiResponseDto<SignInResponseDto>> signInUser(
			@RequestBody @Valid SignInRequestDto requestDto) {
		return ResponseEntity.ok(new ApiResponseDto<>("Authentifié", authService.signInUser(requestDto), true));
	}
}
