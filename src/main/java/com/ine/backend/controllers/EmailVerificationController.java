package com.ine.backend.controllers;

import java.security.Principal;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.ine.backend.dto.ApiResponseDto;
import com.ine.backend.entities.User;
import com.ine.backend.services.EmailVerificationService;

import lombok.RequiredArgsConstructor;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/v1/email")
@RequiredArgsConstructor
public class EmailVerificationController {
	private final EmailVerificationService emailVerificationService;

	@GetMapping("/resend-verification")
	public ResponseEntity<ApiResponseDto<Void>> resendVerification(Principal principal) {
		emailVerificationService.resendVerificationToken(principal.getName());
		return ResponseEntity.ok(new ApiResponseDto<>("Un code de verification a été envoyé.", null, true));
	}

	@GetMapping("/verify")
	public ResponseEntity<ApiResponseDto<Void>> verifyEmail(Principal principal, @RequestParam String token) {
		final User verifiedUser = emailVerificationService.verifyEmail(principal.getName(), token);
		return ResponseEntity.ok(new ApiResponseDto<>("l'émail a été verifié", null, true));
	}
}
