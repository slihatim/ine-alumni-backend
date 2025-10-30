package com.ine.backend.services;

public interface PasswordResetService {
	public void sendPasswordResetToken(String email);

	public void resetPassword(String email, String token, String newPassword);
}
