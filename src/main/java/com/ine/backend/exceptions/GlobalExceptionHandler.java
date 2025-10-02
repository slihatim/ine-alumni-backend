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
	public ResponseEntity<ApiResponseDto<String>> handleMethodArgumentNotValidException(
			MethodArgumentNotValidException ex) {
		return ResponseEntity.badRequest().body(new ApiResponseDto(ex.getMessage(), null, false));
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

	@ExceptionHandler(value = Exception.class)
	public ResponseEntity<ApiResponseDto<String>> handleException(Exception ex) {
		Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);
		logger.error(ex.getMessage());

		return ResponseEntity.status(HttpStatus.BAD_REQUEST)
				.body(new ApiResponseDto<>("Erreur, Veuillez ressayer.", null, false));
	}
}
