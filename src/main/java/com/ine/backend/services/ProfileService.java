package com.ine.backend.services;

import com.ine.backend.dto.*;

public interface ProfileService {

	/**
	 * Get current user's profile
	 *
	 * @param userEmail
	 *            the authenticated user's email
	 * @return ProfileResponseDto containing user profile information
	 */
	ProfileResponseDto getCurrentUserProfile(String userEmail);

	/**
	 * Get any user's profile (admin only or same user)
	 *
	 * @param userId
	 *            the user ID to retrieve
	 * @param currentUserEmail
	 *            the authenticated user's email
	 * @return ProfileResponseDto containing user profile information
	 */
	ProfileResponseDto getUserProfile(Long userId, String currentUserEmail);

	/**
	 * Update current user's profile
	 *
	 * @param userEmail
	 *            the authenticated user's email
	 * @param updateRequest
	 *            the profile update request
	 * @return updated ProfileResponseDto
	 */
	ProfileResponseDto updateCurrentUserProfile(String userEmail, ProfileUpdateRequestDto updateRequest);

	/**
	 * Update any user's profile (admin only)
	 *
	 * @param userId
	 *            the user ID to update
	 * @param currentUserEmail
	 *            the authenticated user's email (must be admin)
	 * @param updateRequest
	 *            the profile update request
	 * @return updated ProfileResponseDto
	 */
	ProfileResponseDto updateUserProfile(Long userId, String currentUserEmail, ProfileUpdateRequestDto updateRequest);

	/**
	 * Change current user's email
	 *
	 * @param userEmail
	 *            the authenticated user's email
	 * @param changeEmailRequest
	 *            the new email request
	 * @return ChangeEmailResponseDto containing new email and token
	 */
	ChangeEmailResponseDto changeUserEmail(String userEmail, ChangeEmailRequestDto changeEmailRequest);

	/**
	 * Change current user's password
	 *
	 * @param userEmail
	 *            the authenticated user's email
	 * @param changePasswordRequest
	 *            the password change request
	 * @return ChangePasswordResponseDto containing new token
	 */
	ChangePasswordResponseDto changeUserPassword(String userEmail, ChangePasswordRequestDto changePasswordRequest);

	/**
	 * Deactivate user account (soft delete)
	 *
	 * @param userEmail
	 *            the authenticated user's email
	 * @return success message
	 */
	String deactivateAccount(String userEmail);

	/**
	 * Delete any user account (hard delete) - Super admin only
	 *
	 * @param userId
	 *            the user ID to delete
	 * @param currentUserEmail
	 *            the authenticated admin's email
	 * @return success message
	 */
	String deleteUserAccount(Long userId, String currentUserEmail);
}
