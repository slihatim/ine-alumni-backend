package com.ine.backend.security;

import org.springframework.http.HttpStatus;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.ine.backend.entities.User;
import com.ine.backend.services.UserService;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EmailVerificationService {
	private final OtpService otpService;
	private final UserService userService;
	private final JavaMailSender mailSender;

	@Async
	public void sendVerificationToken(String email) {
		final String token = otpService.generateAndStoreOtp(email);

		SimpleMailMessage message = new SimpleMailMessage();
		message.setTo(email);
		message.setSubject("Ine Alumni Email Verification");
		message.setFrom("System");
		message.setText("Verification code: " + token);

		mailSender.send(message);
	}

	public void resendVerificationToken(String email) {
		User user = userService.findByEmail(email);
		if (user != null && !user.getIsEmailVerified()) {
			sendVerificationToken(email);
		} else {
			throw new ResponseStatusException(HttpStatus.CONFLICT, "l'utilisateur n'existe pas, ou deja vérifié");
		}
	}

	@Transactional
	public User verifyEmail(String email, String otp) {
		final User user = userService.findByEmail(email);
		if (user.getIsEmailVerified()) {
			throw new ResponseStatusException(HttpStatus.CONFLICT, "l'émail est deja vérifié.");
		}

		if (!otpService.isOtpValid(email, otp)) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Token est invalide ou expiré");
		}
		otpService.deleteOtp(email);

		user.setIsEmailVerified(true);
		return user;
	}
}
