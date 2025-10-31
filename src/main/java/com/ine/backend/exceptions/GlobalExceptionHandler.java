package com.ine.backend.exceptions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

import com.ine.backend.dto.ApiResponseDto;

@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(value = MethodArgumentNotValidException.class)
	public ResponseEntity<ApiResponseDto<java.util.Map<String, String>>> handleMethodArgumentNotValidException(
			MethodArgumentNotValidException ex) {
		Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

		java.util.Map<String, String> errors = new java.util.HashMap<>();
		ex.getBindingResult().getFieldErrors().forEach(error -> {
			String fieldName = error.getField();
			String errorMessage = error.getDefaultMessage();
			errors.put(fieldName, errorMessage);
			logger.warn("Validation error on field '{}': {}", fieldName, errorMessage);
		});

		String message = errors.size() == 1
				? errors.values().iterator().next()
				: "Validation failed for " + errors.size() + " field(s)";

		return ResponseEntity.badRequest().body(new ApiResponseDto<>(message, errors, false));
	}

	@ExceptionHandler(value = UserAlreadyExistsException.class)
	public ResponseEntity<ApiResponseDto<String>> handleUserAlreadyExistsException(UserAlreadyExistsException ex) {
		return ResponseEntity.status(HttpStatus.CONFLICT).body(new ApiResponseDto<>(ex.getMessage(), null, false));
	}

	@ExceptionHandler(value = ResponseStatusException.class)
	public ResponseEntity<ApiResponseDto<String>> handleResponseStatusException(ResponseStatusException ex) {
		return ResponseEntity.status(ex.getStatusCode()).body(new ApiResponseDto<>(ex.getMessage(), null, false));
	}

	@ExceptionHandler(value = ProfileNotFoundException.class)
	public ResponseEntity<ApiResponseDto<String>> handleProfileNotFoundException(ProfileNotFoundException ex) {
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponseDto<>(ex.getMessage(), null, false));
	}

	@ExceptionHandler(value = UnauthorizedProfileAccessException.class)
	public ResponseEntity<ApiResponseDto<String>> handleUnauthorizedProfileAccessException(
			UnauthorizedProfileAccessException ex) {
		return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new ApiResponseDto<>(ex.getMessage(), null, false));
	}

	@ExceptionHandler(value = InvalidPasswordException.class)
	public ResponseEntity<ApiResponseDto<String>> handleInvalidPasswordException(InvalidPasswordException ex) {
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponseDto<>(ex.getMessage(), null, false));
	}

	@ExceptionHandler(value = IllegalArgumentException.class)
	public ResponseEntity<ApiResponseDto<String>> handleIllegalArgumentException(IllegalArgumentException ex) {
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponseDto<>(ex.getMessage(), null, false));
	}

	@ExceptionHandler(value = Exception.class)
	public ResponseEntity<ApiResponseDto<String>> handleException(Exception ex) {
		Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);
		logger.error("Unexpected error occurred: {}", ex.getMessage(), ex);

		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
				.body(new ApiResponseDto<>("An unexpected error occurred. Please try again.", null, false));
	}

	@ExceptionHandler(value = EmailAlreadyVerifiedException.class)
	public ResponseEntity<ApiResponseDto<String>> handleEmailAlreadyVerifiedException(
			EmailAlreadyVerifiedException ex) {
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponseDto<>(ex.getMessage(), null, false));
	}

	@ExceptionHandler(value = InvalidToken.class)
	public ResponseEntity<ApiResponseDto<String>> invalidToken(InvalidToken ex) {
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponseDto<>(ex.getMessage(), null, false));
	}

	@ExceptionHandler(value = UserNotFoundException.class)
	public ResponseEntity<ApiResponseDto<String>> handleUserNotFoundException(UserNotFoundException ex) {
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponseDto<>(ex.getMessage(), null, false));
	}

	@ExceptionHandler(value = NullPointerException.class)
	public ResponseEntity<ApiResponseDto<String>> handleNullPointerException(NullPointerException ex) {
		Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);
		logger.error("NullPointerException occurred: {}", ex.getMessage(), ex);

		// This typically happens when Principal is null due to authentication failure
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
				.body(new ApiResponseDto<>("Authentication failed. Please login again.", null, false));
	}
}
