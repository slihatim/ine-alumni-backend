package com.ine.backend.services;

import org.springframework.http.HttpStatus;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.ine.backend.entities.InptUser;
import com.ine.backend.security.OtpService;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PasswordResetServiceImpl implements PasswordResetService {
	private final OtpService otpService;
	private final UserService userService;
	private final JavaMailSender mailSender;
	private final PasswordEncoder passwordEncoder;

	@Async
	@Override
	public void sendPasswordResetToken(String email) {
		final InptUser user = userService.findByEmail(email);
		if (user == null) {
			// Security: Don't reveal whether email exists - silently return
			return;
		}

		final String token = otpService.generateAndStoreOtp(email);

		SimpleMailMessage message = new SimpleMailMessage();
		message.setTo(email);
		message.setSubject("Ine Alumni - Réinitialisation du mot de passe");
		message.setFrom("System");
		message.setText(
				"Votre code de réinitialisation du mot de passe: " + token + "\n\nCe code expirera dans 60 minutes.");

		mailSender.send(message);
	}

	@Transactional
	@Override
	public void resetPassword(String email, String token, String newPassword) {
		final InptUser user = userService.findByEmail(email);
		if (user == null) {
			// Security: Same error message as invalid token to prevent enumeration
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Le code est invalide ou expiré.");
		}

		if (!otpService.isOtpValid(email, token)) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Le code est invalide ou expiré.");
		}

		otpService.deleteOtp(email);

		user.setPassword(passwordEncoder.encode(newPassword));
		userService.saveUser(user);
	}
}
