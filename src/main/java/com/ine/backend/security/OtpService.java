package com.ine.backend.security;

import java.security.SecureRandom;
import java.time.Duration;
import java.util.Objects;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OtpService {
	private static final SecureRandom secureRandom = new SecureRandom();

	private final RedisTemplate<String, String> redisTemplate;

	public String generateAndStoreOtp(String email) {
		final String otp = generateOtp("ABCDEFG123456789", 6);
		final String cacheKey = getCacheKey(email);

		redisTemplate.opsForValue().set(cacheKey, otp, Duration.ofMinutes(60));
		return otp;
	}

	public boolean isOtpValid(String email, String otp) {
		final String cacheKey = getCacheKey(email);
		return Objects.equals(redisTemplate.opsForValue().get(cacheKey), otp);
	}

	public void deleteOtp(String email) {
		final String cacheKey = getCacheKey(email);
		redisTemplate.delete(cacheKey);
	}

	private String getCacheKey(String email) {
		return "otp:%s".formatted(email);
	}

	private String generateOtp(String characters, Integer length) {
		StringBuilder otp = new StringBuilder(length);
		for (int i = 0; i < length; i++) {
			int index = secureRandom.nextInt(characters.length());
			otp.append(characters.charAt(index));
		}
		return otp.toString();
	}
}
