package com.ine.backend.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.ine.backend.dto.ForgotPasswordRequestDto;
import com.ine.backend.dto.ResetPasswordRequestDto;
import com.ine.backend.services.PasswordResetService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/v1/password")
@RequiredArgsConstructor
public class PasswordResetController {
	private final PasswordResetService passwordResetService;

	@PostMapping("/forgot")
	public ResponseEntity<String> forgotPassword(@RequestBody @Valid ForgotPasswordRequestDto requestDto) {
		passwordResetService.sendPasswordResetToken(requestDto.getEmail());
		return ResponseEntity.ok("Un code de réinitialisation a été envoyé à votre adresse email.");
	}

	@PostMapping("/reset")
	public ResponseEntity<String> resetPassword(@RequestBody @Valid ResetPasswordRequestDto requestDto) {
		passwordResetService.resetPassword(requestDto.getEmail(), requestDto.getToken(), requestDto.getNewPassword());
		return ResponseEntity.ok("Votre mot de passe a été réinitialisé avec succès.");
	}
}
