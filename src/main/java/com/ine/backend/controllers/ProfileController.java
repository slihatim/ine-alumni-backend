package com.ine.backend.controllers;

import java.security.Principal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.ine.backend.dto.ApiResponseDto;
import com.ine.backend.dto.ChangeEmailRequestDto;
import com.ine.backend.dto.ChangePasswordRequestDto;
import com.ine.backend.dto.ProfileResponseDto;
import com.ine.backend.dto.ProfileUpdateRequestDto;
import com.ine.backend.services.ProfileService;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/v1/profile")
@AllArgsConstructor
public class ProfileController {

	private static final Logger log = LoggerFactory.getLogger(ProfileController.class);

	private final ProfileService profileService;

	@GetMapping("/me")
	public ResponseEntity<ApiResponseDto<ProfileResponseDto>> getCurrentUserProfile(Principal principal) {
		try {
			log.info("Getting current user profile for: {}", principal.getName());
			ProfileResponseDto profile = profileService.getCurrentUserProfile(principal.getName());
			return new ResponseEntity<>(ApiResponseDto.<ProfileResponseDto>builder().message("Profile retrieved")
					.response(profile).isSuccess(true).build(), HttpStatus.OK);
		} catch (Exception e) {
			log.error("Failed to get current user profile. Error: {}", e.getMessage(), e);
			return new ResponseEntity<>(ApiResponseDto.<ProfileResponseDto>builder().message(e.getMessage())
					.response(null).isSuccess(false).build(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/{userId}")
	@PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_SUPER_ADMIN')")
	public ResponseEntity<ApiResponseDto<ProfileResponseDto>> getUserProfile(@PathVariable Long userId,
			Principal principal) {
		try {
			log.info("Getting user profile for ID: {} by user: {}", userId, principal.getName());
			ProfileResponseDto profile = profileService.getUserProfile(userId, principal.getName());
			return new ResponseEntity<>(ApiResponseDto.<ProfileResponseDto>builder().message("Profile retrieved")
					.response(profile).isSuccess(true).build(), HttpStatus.OK);
		} catch (RuntimeException e) {
			log.warn("Profile access error for user {}: {}", userId, e.getMessage(), e);
			return new ResponseEntity<>(ApiResponseDto.<ProfileResponseDto>builder().message(e.getMessage())
					.response(null).isSuccess(false).build(), HttpStatus.FORBIDDEN);
		} catch (Exception e) {
			log.error("Failed to get user profile {}. Error: {}", userId, e.getMessage(), e);
			return new ResponseEntity<>(ApiResponseDto.<ProfileResponseDto>builder().message(e.getMessage())
					.response(null).isSuccess(false).build(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PutMapping("/me")
	public ResponseEntity<ApiResponseDto<ProfileResponseDto>> updateCurrentUserProfile(
			@RequestBody @Valid ProfileUpdateRequestDto updateRequest, Principal principal) {
		try {
			log.info("Updating current user profile for: {}", principal.getName());
			ProfileResponseDto updatedProfile = profileService.updateCurrentUserProfile(principal.getName(),
					updateRequest);
			return new ResponseEntity<>(ApiResponseDto.<ProfileResponseDto>builder().message("Profile updated")
					.response(updatedProfile).isSuccess(true).build(), HttpStatus.OK);
		} catch (RuntimeException e) {
			log.warn("Business error while updating profile for user {}. Error: {}", principal.getName(),
					e.getMessage(), e);
			return new ResponseEntity<>(ApiResponseDto.<ProfileResponseDto>builder().message(e.getMessage())
					.response(null).isSuccess(false).build(), HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			log.error("Failed to update profile for user {}. Error: {}", principal.getName(), e.getMessage(), e);
			return new ResponseEntity<>(ApiResponseDto.<ProfileResponseDto>builder().message(e.getMessage())
					.response(null).isSuccess(false).build(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PutMapping("/{userId}")
	@PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_SUPER_ADMIN')")
	public ResponseEntity<ApiResponseDto<ProfileResponseDto>> updateUserProfile(@PathVariable Long userId,
			@RequestBody @Valid ProfileUpdateRequestDto updateRequest, Principal principal) {
		try {
			log.info("Admin updating user profile for ID: {} by admin: {}", userId, principal.getName());
			ProfileResponseDto updatedProfile = profileService.updateUserProfile(userId, principal.getName(),
					updateRequest);
			return new ResponseEntity<>(ApiResponseDto.<ProfileResponseDto>builder().message("Profile updated")
					.response(updatedProfile).isSuccess(true).build(), HttpStatus.OK);
		} catch (RuntimeException e) {
			log.warn("Business error while updating profile {} by admin {}. Error: {}", userId, principal.getName(),
					e.getMessage(), e);
			return new ResponseEntity<>(ApiResponseDto.<ProfileResponseDto>builder().message(e.getMessage())
					.response(null).isSuccess(false).build(), HttpStatus.FORBIDDEN);
		} catch (Exception e) {
			log.error("Failed to update profile {} by admin {}. Error: {}", userId, principal.getName(), e.getMessage(),
					e);
			return new ResponseEntity<>(ApiResponseDto.<ProfileResponseDto>builder().message(e.getMessage())
					.response(null).isSuccess(false).build(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PutMapping("/change-email")
	public ResponseEntity<ApiResponseDto<String>> changeUserEmail(
			@RequestBody @Valid ChangeEmailRequestDto changeEmailRequest, Principal principal) {
		try {
			log.info("Changing email for user: {}", principal.getName());
			String message = profileService.changeUserEmail(principal.getName(), changeEmailRequest);
			return new ResponseEntity<>(
					ApiResponseDto.<String>builder().message(message).response(null).isSuccess(true).build(),
					HttpStatus.OK);
		} catch (RuntimeException e) {
			log.warn("Business error while changing email for user {}. Error: {}", principal.getName(), e.getMessage(),
					e);
			return new ResponseEntity<>(
					ApiResponseDto.<String>builder().message(e.getMessage()).response(null).isSuccess(false).build(),
					HttpStatus.CONFLICT);
		} catch (Exception e) {
			log.error("Failed to change email for user {}. Error: {}", principal.getName(), e.getMessage(), e);
			return new ResponseEntity<>(
					ApiResponseDto.<String>builder().message(e.getMessage()).response(null).isSuccess(false).build(),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PutMapping("/change-password")
	public ResponseEntity<ApiResponseDto<String>> changeUserPassword(
			@RequestBody @Valid ChangePasswordRequestDto changePasswordRequest, Principal principal) {
		try {
			log.info("Changing password for user: {}", principal.getName());
			String message = profileService.changeUserPassword(principal.getName(), changePasswordRequest);
			return new ResponseEntity<>(
					ApiResponseDto.<String>builder().message(message).response(null).isSuccess(true).build(),
					HttpStatus.OK);
		} catch (RuntimeException e) {
			log.warn("Business error while changing password for user {}. Error: {}", principal.getName(),
					e.getMessage(), e);
			return new ResponseEntity<>(
					ApiResponseDto.<String>builder().message(e.getMessage()).response(null).isSuccess(false).build(),
					HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			log.error("Failed to change password for user {}. Error: {}", principal.getName(), e.getMessage(), e);
			return new ResponseEntity<>(
					ApiResponseDto.<String>builder().message(e.getMessage()).response(null).isSuccess(false).build(),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PutMapping("/deactivate")
	public ResponseEntity<ApiResponseDto<String>> deactivateAccount(Principal principal) {
		try {
			log.info("Deactivating account for user: {}", principal.getName());
			String message = profileService.deactivateAccount(principal.getName());
			return new ResponseEntity<>(
					ApiResponseDto.<String>builder().message(message).response(null).isSuccess(true).build(),
					HttpStatus.OK);
		} catch (RuntimeException e) {
			log.warn("Business error while deactivating account for user {}. Error: {}", principal.getName(),
					e.getMessage(), e);
			return new ResponseEntity<>(
					ApiResponseDto.<String>builder().message(e.getMessage()).response(null).isSuccess(false).build(),
					HttpStatus.NOT_FOUND);
		} catch (Exception e) {
			log.error("Failed to deactivate account for user {}. Error: {}", principal.getName(), e.getMessage(), e);
			return new ResponseEntity<>(
					ApiResponseDto.<String>builder().message(e.getMessage()).response(null).isSuccess(false).build(),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@DeleteMapping("/{userId}")
	@PreAuthorize("hasRole('ROLE_SUPER_ADMIN')")
	public ResponseEntity<ApiResponseDto<String>> deleteUserAccount(@PathVariable Long userId, Principal principal) {
		try {
			log.info("Super admin deleting user account ID: {} by admin: {}", userId, principal.getName());
			String message = profileService.deleteUserAccount(userId, principal.getName());
			return new ResponseEntity<>(
					ApiResponseDto.<String>builder().message(message).response(null).isSuccess(true).build(),
					HttpStatus.OK);
		} catch (RuntimeException e) {
			log.warn("Business error while deleting user account {} by admin {}. Error: {}", userId,
					principal.getName(), e.getMessage(), e);
			return new ResponseEntity<>(
					ApiResponseDto.<String>builder().message(e.getMessage()).response(null).isSuccess(false).build(),
					HttpStatus.FORBIDDEN);
		} catch (Exception e) {
			log.error("Failed to delete user account {} by admin {}. Error: {}", userId, principal.getName(),
					e.getMessage(), e);
			return new ResponseEntity<>(
					ApiResponseDto.<String>builder().message(e.getMessage()).response(null).isSuccess(false).build(),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
