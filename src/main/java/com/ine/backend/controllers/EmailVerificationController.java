package com.ine.backend.controllers;

import java.security.Principal;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.ine.backend.exceptions.UserNotFoundException;
import com.ine.backend.services.EmailVerificationService;

import lombok.RequiredArgsConstructor;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/v1/email")
@RequiredArgsConstructor
public class EmailVerificationController {
	private final EmailVerificationService emailVerificationService;

	@GetMapping("/resend-verification")
	public ResponseEntity<String> resendVerification(Principal principal) {
		if (principal == null) {
			throw new UserNotFoundException("User not found. Please login again.");
		}
		emailVerificationService.resendVerificationToken(principal.getName());
		return ResponseEntity.ok("A verification code has been sent to your email.");
	}

	@GetMapping("/verify")
	public ResponseEntity<String> verifyEmail(Principal principal, @RequestParam String token) {
		if (principal == null) {
			throw new UserNotFoundException("User not found. Please login again.");
		}
		emailVerificationService.verifyEmail(principal.getName(), token);
		return ResponseEntity.ok("Email verified.");
	}
}
