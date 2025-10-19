package com.ine.backend.controllers;

import java.security.Principal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.ine.backend.dto.ApiResponseDto;
import com.ine.backend.dto.ChangeEmailRequestDto;
import com.ine.backend.dto.ChangeEmailResponseDto;
import com.ine.backend.dto.ChangePasswordRequestDto;
import com.ine.backend.dto.ChangePasswordResponseDto;
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
	@PreAuthorize("hasAuthority('profile:read')")
	public ResponseEntity<ApiResponseDto<ProfileResponseDto>> getCurrentUserProfile(Principal principal) {
		log.info("Getting current user profile for: {}", principal.getName());
		ProfileResponseDto profile = profileService.getCurrentUserProfile(principal.getName());
		return ResponseEntity.ok(ApiResponseDto.<ProfileResponseDto>builder().message("Profile retrieved successfully")
				.response(profile).isSuccess(true).build());
	}

	@GetMapping("/{userId}")
	@PreAuthorize("hasAuthority('profile:read') or hasAuthority('profile:read:all')")
	public ResponseEntity<ApiResponseDto<ProfileResponseDto>> getUserProfile(@PathVariable Long userId,
			Principal principal) {
		log.info("Getting user profile for ID: {} by user: {}", userId, principal.getName());
		ProfileResponseDto profile = profileService.getUserProfile(userId, principal.getName());
		return ResponseEntity.ok(ApiResponseDto.<ProfileResponseDto>builder().message("Profile retrieved successfully")
				.response(profile).isSuccess(true).build());
	}

	@PatchMapping("/me")
	@PreAuthorize("hasAuthority('profile:update')")
	public ResponseEntity<ApiResponseDto<ProfileResponseDto>> updateCurrentUserProfile(
			@RequestBody @Valid ProfileUpdateRequestDto updateRequest, Principal principal) {
		log.info("Updating current user profile for: {}", principal.getName());
		ProfileResponseDto updatedProfile = profileService.updateCurrentUserProfile(principal.getName(), updateRequest);
		return ResponseEntity.ok(ApiResponseDto.<ProfileResponseDto>builder().message("Profile updated successfully")
				.response(updatedProfile).isSuccess(true).build());
	}

	@PatchMapping("/{userId}")
	@PreAuthorize("hasAuthority('profile:update') or hasAuthority('profile:update:all')")
	public ResponseEntity<ApiResponseDto<ProfileResponseDto>> updateUserProfile(@PathVariable Long userId,
			@RequestBody @Valid ProfileUpdateRequestDto updateRequest, Principal principal) {
		log.info("Admin updating user profile for ID: {} by admin: {}", userId, principal.getName());
		ProfileResponseDto updatedProfile = profileService.updateUserProfile(userId, principal.getName(),
				updateRequest);
		return ResponseEntity.ok(ApiResponseDto.<ProfileResponseDto>builder().message("Profile updated successfully")
				.response(updatedProfile).isSuccess(true).build());
	}

	@PatchMapping("/change-email")
	@PreAuthorize("hasAuthority('profile:update')")
	public ResponseEntity<ApiResponseDto<ChangeEmailResponseDto>> changeUserEmail(
			@RequestBody @Valid ChangeEmailRequestDto changeEmailRequest, Principal principal) {
		log.info("Changing email for user: {}", principal.getName());
		ChangeEmailResponseDto response = profileService.changeUserEmail(principal.getName(), changeEmailRequest);
		return ResponseEntity.ok(ApiResponseDto.<ChangeEmailResponseDto>builder().message(
				"Email address has been successfully changed. A verification code has been sent to your new email address.")
				.response(response).isSuccess(true).build());
	}

	@PatchMapping("/change-password")
	@PreAuthorize("hasAuthority('profile:update')")
	public ResponseEntity<ApiResponseDto<ChangePasswordResponseDto>> changeUserPassword(
			@RequestBody @Valid ChangePasswordRequestDto changePasswordRequest, Principal principal) {
		log.info("Changing password for user: {}", principal.getName());
		ChangePasswordResponseDto response = profileService.changeUserPassword(principal.getName(),
				changePasswordRequest);
		return ResponseEntity.ok(ApiResponseDto.<ChangePasswordResponseDto>builder()
				.message("Password has been successfully changed.").response(response).isSuccess(true).build());
	}

	@PatchMapping("/deactivate")
	@PreAuthorize("hasAuthority('profile:update')")
	public ResponseEntity<ApiResponseDto<String>> deactivateAccount(Principal principal) {
		log.info("Deactivating account for user: {}", principal.getName());
		String message = profileService.deactivateAccount(principal.getName());
		return ResponseEntity
				.ok(ApiResponseDto.<String>builder().message(message).response(null).isSuccess(true).build());
	}

	@DeleteMapping("/{userId}")
	@PreAuthorize("hasAuthority('profile:delete:all')")
	public ResponseEntity<ApiResponseDto<String>> deleteUserAccount(@PathVariable Long userId, Principal principal) {
		log.info("Super admin deleting user account ID: {} by admin: {}", userId, principal.getName());
		String message = profileService.deleteUserAccount(userId, principal.getName());
		return ResponseEntity
				.ok(ApiResponseDto.<String>builder().message(message).response(null).isSuccess(true).build());
	}
}
