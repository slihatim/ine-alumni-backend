package com.ine.backend.exceptions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

import com.ine.backend.dto.ErrorResponseDto;

@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(value = MethodArgumentNotValidException.class)
	public ResponseEntity<ErrorResponseDto> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
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

		return ResponseEntity.badRequest().body(ErrorResponseDto.builder().message(message).errors(errors).build());
	}

	@ExceptionHandler(value = UserAlreadyExistsException.class)
	public ResponseEntity<ErrorResponseDto> handleUserAlreadyExistsException(UserAlreadyExistsException ex) {
		return ResponseEntity.status(HttpStatus.CONFLICT)
				.body(ErrorResponseDto.builder().message(ex.getMessage()).build());
	}

	@ExceptionHandler(value = ResponseStatusException.class)
	public ResponseEntity<ErrorResponseDto> handleResponseStatusException(ResponseStatusException ex) {
		return ResponseEntity.status(ex.getStatusCode())
				.body(ErrorResponseDto.builder().message(ex.getMessage()).build());
	}

	@ExceptionHandler(value = ProfileNotFoundException.class)
	public ResponseEntity<ErrorResponseDto> handleProfileNotFoundException(ProfileNotFoundException ex) {
		return ResponseEntity.status(HttpStatus.NOT_FOUND)
				.body(ErrorResponseDto.builder().message(ex.getMessage()).build());
	}

	@ExceptionHandler(value = UnauthorizedProfileAccessException.class)
	public ResponseEntity<ErrorResponseDto> handleUnauthorizedProfileAccessException(
			UnauthorizedProfileAccessException ex) {
		return ResponseEntity.status(HttpStatus.FORBIDDEN)
				.body(ErrorResponseDto.builder().message(ex.getMessage()).build());
	}

	@ExceptionHandler(value = InvalidPasswordException.class)
	public ResponseEntity<ErrorResponseDto> handleInvalidPasswordException(InvalidPasswordException ex) {
		return ResponseEntity.status(HttpStatus.BAD_REQUEST)
				.body(ErrorResponseDto.builder().message(ex.getMessage()).build());
	}

	@ExceptionHandler(value = IllegalArgumentException.class)
	public ResponseEntity<ErrorResponseDto> handleIllegalArgumentException(IllegalArgumentException ex) {
		return ResponseEntity.status(HttpStatus.BAD_REQUEST)
				.body(ErrorResponseDto.builder().message(ex.getMessage()).build());
	}

	@ExceptionHandler(value = Exception.class)
	public ResponseEntity<ErrorResponseDto> handleException(Exception ex) {
		Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);
		logger.error("Unexpected error occurred: {}", ex.getMessage(), ex);

		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
				.body(ErrorResponseDto.builder().message("An unexpected error occurred. Please try again.").build());
	}

	@ExceptionHandler(value = EmailAlreadyVerifiedException.class)
	public ResponseEntity<ErrorResponseDto> handleEmailAlreadyVerifiedException(EmailAlreadyVerifiedException ex) {
		return ResponseEntity.status(HttpStatus.BAD_REQUEST)
				.body(ErrorResponseDto.builder().message(ex.getMessage()).build());
	}

	@ExceptionHandler(value = InvalidToken.class)
	public ResponseEntity<ErrorResponseDto> invalidToken(InvalidToken ex) {
		return ResponseEntity.status(HttpStatus.BAD_REQUEST)
				.body(ErrorResponseDto.builder().message(ex.getMessage()).build());
	}

	@ExceptionHandler(value = UserNotFoundException.class)
	public ResponseEntity<ErrorResponseDto> handleUserNotFoundException(UserNotFoundException ex) {
		return ResponseEntity.status(HttpStatus.NOT_FOUND)
				.body(ErrorResponseDto.builder().message(ex.getMessage()).build());
	}

	@ExceptionHandler(value = NullPointerException.class)
	public ResponseEntity<ErrorResponseDto> handleNullPointerException(NullPointerException ex) {
		Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);
		logger.error("NullPointerException occurred: {}", ex.getMessage(), ex);

		// This typically happens when Principal is null due to authentication failure
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
				.body(ErrorResponseDto.builder().message("Authentication failed. Please login again.").build());
	}
}
