package com.ine.backend.services;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.ine.backend.entities.User;
import com.ine.backend.exceptions.EmailAlreadyVerifiedException;
import com.ine.backend.exceptions.InvalidToken;
import com.ine.backend.exceptions.UserNotFoundException;
import com.ine.backend.security.OtpService;

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
		if (user == null) {
			throw new UserNotFoundException("User not found with email: " + email);
		}
		if (user.getIsEmailVerified()) {
			throw new EmailAlreadyVerifiedException("Email Already Verified");
		}
		sendVerificationToken(email);
	}

	@Transactional
	public User verifyEmail(String email, String otp) {
		final User user = userService.findByEmail(email);
		if (user == null) {
			throw new UserNotFoundException("User not found with email: " + email);
		}
		if (user.getIsEmailVerified()) {
			throw new EmailAlreadyVerifiedException("Email Already Verified");
		}

		if (!otpService.isOtpValid(email, otp)) {
			throw new InvalidToken("Invalid Token");
		}
		otpService.deleteOtp(email);
		user.setIsEmailVerified(true);
		return user;
	}

}
