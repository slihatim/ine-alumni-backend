package com.ine.backend.services.impl;

import java.time.LocalDate;
import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ine.backend.dto.*;
import com.ine.backend.entities.InptUser;
import com.ine.backend.entities.Role;
import com.ine.backend.exceptions.InvalidPasswordException;
import com.ine.backend.exceptions.ProfileNotFoundException;
import com.ine.backend.exceptions.UnauthorizedProfileAccessException;
import com.ine.backend.exceptions.UserAlreadyExistsException;
import com.ine.backend.mappers.ProfileMapper;
import com.ine.backend.repositories.UserRepository;
import com.ine.backend.security.jwt.JwtUtils;
import com.ine.backend.services.EmailVerificationService;
import com.ine.backend.services.ProfileService;
import com.ine.backend.utils.ProfileValidationUtils;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class ProfileServiceImpl implements ProfileService {

	private static final Logger log = LoggerFactory.getLogger(ProfileServiceImpl.class);

	private final UserRepository userRepository;
	private final ProfileMapper profileMapper;
	private final PasswordEncoder passwordEncoder;
	private final ProfileValidationUtils profileValidationUtils;
	private final EmailVerificationService emailVerificationService;
	private final JwtUtils jwtUtils;

	@Override
	@Transactional(readOnly = true)
	public ProfileResponseDto getCurrentUserProfile(String userEmail) {
		log.info("Getting profile for user: {}", userEmail);

		// Validate input
		if (userEmail == null || userEmail.trim().isEmpty()) {
			log.warn("Get profile failed - user email is null or empty");
			throw new IllegalArgumentException("User email cannot be empty.");
		}

		// Find user - may throw ProfileNotFoundException
		InptUser user = findUserByEmail(userEmail);
		return profileMapper.toProfileResponseDto(user);
	}

	@Override
	@Transactional(readOnly = true)
	public ProfileResponseDto getUserProfile(Long userId, String currentUserEmail) {
		log.info("Getting profile for user ID: {} by user: {}", userId, currentUserEmail);

		// Validate input
		if (userId == null || userId <= 0) {
			log.warn("Get profile failed - invalid user ID: {}", userId);
			throw new IllegalArgumentException("Invalid user ID.");
		}

		if (currentUserEmail == null || currentUserEmail.trim().isEmpty()) {
			log.warn("Get profile failed - current user email is null or empty");
			throw new IllegalArgumentException("Current user email cannot be empty.");
		}

		// Find users - may throw ProfileNotFoundException
		InptUser currentUser = findUserByEmail(currentUserEmail);
		InptUser targetUser = findUserById(userId);

		// Check if user can access this profile - throws
		// UnauthorizedProfileAccessException
		if (!canAccessProfile(currentUser, targetUser)) {
			log.warn("Unauthorized access attempt for user ID: {} by user: {}", userId, currentUserEmail);
			throw new UnauthorizedProfileAccessException("You are not authorized to access this profile.");
		}

		return profileMapper.toProfileResponseDto(targetUser);
	}

	@Override
	public ProfileResponseDto updateCurrentUserProfile(String userEmail, ProfileUpdateRequestDto updateRequest) {
		log.info("Updating profile for user: {}", userEmail);

		// Validate input
		if (userEmail == null || userEmail.trim().isEmpty()) {
			log.warn("Profile update failed - user email is null or empty");
			throw new IllegalArgumentException("User email cannot be empty.");
		}

		if (updateRequest == null) {
			log.warn("Profile update failed for user: {} - update request is null", userEmail);
			throw new IllegalArgumentException("Update request cannot be null.");
		}

		// Find user - may throw ProfileNotFoundException
		InptUser user = findUserByEmail(userEmail);

		// Update fields - may throw IllegalArgumentException
		updateUserFields(user, updateRequest);
		InptUser savedUser = userRepository.save(user);

		log.info("Profile updated successfully for user: {}", userEmail);
		return profileMapper.toProfileResponseDto(savedUser);
	}

	@Override
	public ProfileResponseDto updateUserProfile(Long userId, String currentUserEmail,
			ProfileUpdateRequestDto updateRequest) {
		log.info("Admin updating profile for user ID: {} by admin: {}", userId, currentUserEmail);

		// Validate input
		if (userId == null || userId <= 0) {
			log.warn("Profile update failed - invalid user ID: {}", userId);
			throw new IllegalArgumentException("Invalid user ID.");
		}

		if (currentUserEmail == null || currentUserEmail.trim().isEmpty()) {
			log.warn("Profile update failed - current user email is null or empty");
			throw new IllegalArgumentException("Current user email cannot be empty.");
		}

		if (updateRequest == null) {
			log.warn("Profile update failed for user ID: {} - update request is null", userId);
			throw new IllegalArgumentException("Update request cannot be null.");
		}

		// Find users - may throw ProfileNotFoundException
		InptUser currentUser = findUserByEmail(currentUserEmail);
		InptUser targetUser = findUserById(userId);

		// Only admins can update other users' profiles - throws
		// UnauthorizedProfileAccessException
		if (!isAdminOrSuperAdmin(currentUser)) {
			log.warn("Unauthorized profile update attempt for user ID: {} by non-admin: {}", userId, currentUserEmail);
			throw new UnauthorizedProfileAccessException("Only administrators can modify other users' profiles.");
		}

		// Update fields - may throw IllegalArgumentException
		updateUserFields(targetUser, updateRequest);
		InptUser savedUser = userRepository.save(targetUser);

		log.info("Profile updated successfully for user ID: {} by admin: {}", userId, currentUserEmail);
		return profileMapper.toProfileResponseDto(savedUser);
	}

	@Override
	@Transactional
	public ChangeEmailResponseDto changeUserEmail(String userEmail, ChangeEmailRequestDto changeEmailRequest) {
		log.info("Changing email for user: {} to: {}", userEmail, changeEmailRequest.getNewEmail());

		// Validate input
		if (changeEmailRequest.getNewEmail() == null || changeEmailRequest.getNewEmail().trim().isEmpty()) {
			log.warn("Email change failed for user: {} - new email is null or empty", userEmail);
			throw new IllegalArgumentException("New email address cannot be empty.");
		}

		// Validate email format - already handled by @Email annotation in DTO
		String newEmail = changeEmailRequest.getNewEmail().trim().toLowerCase();

		// Find current user - may throw ProfileNotFoundException
		InptUser user = findUserByEmail(userEmail);

		// Check if trying to change to same email
		if (user.getEmail().equalsIgnoreCase(newEmail)) {
			log.warn("Email change failed for user: {} - new email is same as current email", userEmail);
			throw new IllegalArgumentException("New email address must be different from the current one.");
		}

		// Check if new email already exists - throws UserAlreadyExistsException
		InptUser existingUser = userRepository.findByEmail(newEmail);
		if (existingUser != null) {
			log.warn("Email change failed for user: {} - email already exists: {}", userEmail, newEmail);
			throw new UserAlreadyExistsException("This email address is already in use.");
		}

		user.setEmail(newEmail);
		user.setIsEmailVerified(false); // Require email verification for new email

		userRepository.save(user);

		// Generate new JWT token with updated email
		UserDetailsImpl userDetails = UserDetailsImpl.build(user);
		Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, null,
				userDetails.getAuthorities());
		String newToken = jwtUtils.generateJwtToken(authentication);

		// Send verification token to the new email address
		emailVerificationService.sendVerificationToken(newEmail);

		log.info("Email changed successfully for user: {} to: {}, email verification code was sent", userEmail,
				newEmail);

		return ChangeEmailResponseDto.builder().newEmail(newEmail).token(newToken).type("Bearer").build();
	}

	@Override
	public ChangePasswordResponseDto changeUserPassword(String userEmail,
			ChangePasswordRequestDto changePasswordRequest) {
		log.info("Changing password for user: {}", userEmail);

		// Validate input - throws IllegalArgumentException
		if (changePasswordRequest.getCurrentPassword() == null
				|| changePasswordRequest.getCurrentPassword().isEmpty()) {
			log.warn("Password change failed for user: {} - current password is null or empty", userEmail);
			throw new IllegalArgumentException("Current password cannot be empty.");
		}

		if (changePasswordRequest.getNewPassword() == null || changePasswordRequest.getNewPassword().isEmpty()) {
			log.warn("Password change failed for user: {} - new password is null or empty", userEmail);
			throw new IllegalArgumentException("New password cannot be empty.");
		}

		if (changePasswordRequest.getConfirmPassword() == null
				|| changePasswordRequest.getConfirmPassword().isEmpty()) {
			log.warn("Password change failed for user: {} - confirm password is null or empty", userEmail);
			throw new IllegalArgumentException("Password confirmation cannot be empty.");
		}

		// Validate password strength (minimum 8 characters)
		if (changePasswordRequest.getNewPassword().length() < 8) {
			log.warn("Password change failed for user: {} - new password too short", userEmail);
			throw new InvalidPasswordException("New password must be at least 8 characters long.");
		}

		// Find user - may throw ProfileNotFoundException
		InptUser user = findUserByEmail(userEmail);

		// Verify current password - throws InvalidPasswordException
		if (!passwordEncoder.matches(changePasswordRequest.getCurrentPassword(), user.getPassword())) {
			log.warn("Password change failed for user: {} - current password incorrect", userEmail);
			throw new InvalidPasswordException("Current password is incorrect.");
		}

		// Verify password confirmation - throws InvalidPasswordException
		if (!changePasswordRequest.getNewPassword().equals(changePasswordRequest.getConfirmPassword())) {
			log.warn("Password change failed for user: {} - password confirmation mismatch", userEmail);
			throw new InvalidPasswordException("Password confirmation does not match.");
		}

		// Check if new password is different from current - throws
		// InvalidPasswordException
		if (passwordEncoder.matches(changePasswordRequest.getNewPassword(), user.getPassword())) {
			log.warn("Password change failed for user: {} - new password same as current", userEmail);
			throw new InvalidPasswordException("New password must be different from the current password.");
		}

		user.setPassword(passwordEncoder.encode(changePasswordRequest.getNewPassword()));
		userRepository.save(user);

		// Generate new JWT token
		UserDetailsImpl userDetails = UserDetailsImpl.build(user);
		Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, null,
				userDetails.getAuthorities());
		String newToken = jwtUtils.generateJwtToken(authentication);

		log.info("Password changed successfully for user: {}", userEmail);

		return ChangePasswordResponseDto.builder().token(newToken).type("Bearer").build();
	}

	@Override
	public String deactivateAccount(String userEmail) {
		log.info("Deactivating account for user: {}", userEmail);

		// Validate input
		if (userEmail == null || userEmail.trim().isEmpty()) {
			log.warn("Account deactivation failed - user email is null or empty");
			throw new IllegalArgumentException("User email cannot be empty.");
		}

		// Find user - may throw ProfileNotFoundException
		InptUser user = findUserByEmail(userEmail);

		// Check if account is already deactivated
		if (!user.getIsAccountVerified()) {
			log.warn("Account deactivation failed for user: {} - account already deactivated", userEmail);
			throw new IllegalArgumentException("Account is already deactivated.");
		}

		user.setIsAccountVerified(false);
		user.setIsEmailVerified(false);
		userRepository.save(user);

		log.info("Account deactivated successfully for user: {}", userEmail);
		return "Account has been successfully deactivated.";
	}

	@Override
	public String deleteUserAccount(Long userId, String currentUserEmail) {
		log.info("Admin deleting user account ID: {} by admin: {}", userId, currentUserEmail);

		// Validate input
		if (userId == null || userId <= 0) {
			log.warn("Account deletion failed - invalid user ID: {}", userId);
			throw new IllegalArgumentException("Invalid user ID.");
		}

		if (currentUserEmail == null || currentUserEmail.trim().isEmpty()) {
			log.warn("Account deletion failed - current user email is null or empty");
			throw new IllegalArgumentException("Current user email cannot be empty.");
		}

		// Find users - may throw ProfileNotFoundException
		InptUser currentUser = findUserByEmail(currentUserEmail);
		InptUser targetUser = findUserById(userId);

		// Only super admins can delete accounts - throws
		// UnauthorizedProfileAccessException
		if (!isSuperAdmin(currentUser)) {
			log.warn("Unauthorized account deletion attempt for user ID: {} by non-super-admin: {}", userId,
					currentUserEmail);
			throw new UnauthorizedProfileAccessException("Only super administrators can delete user accounts.");
		}

		// Prevent self-deletion - throws UnauthorizedProfileAccessException
		if (Objects.equals(currentUser.getId(), targetUser.getId())) {
			log.warn("Self-deletion attempt blocked for user ID: {} by admin: {}", userId, currentUserEmail);
			throw new UnauthorizedProfileAccessException("You cannot delete your own account.");
		}

		userRepository.delete(targetUser);

		log.info("User account deleted successfully ID: {} by admin: {}", userId, currentUserEmail);
		return "User account has been successfully deleted.";
	}

	// Helper methods
	private InptUser findUserByEmail(String email) {
		InptUser user = userRepository.findByEmail(email);
		if (user == null) {
			throw new ProfileNotFoundException("User not found with email: " + email);
		}
		return user;
	}

	private InptUser findUserById(Long id) {
		return userRepository.findById(id)
				.orElseThrow(() -> new ProfileNotFoundException("User not found with ID: " + id));
	}

	private boolean canAccessProfile(InptUser currentUser, InptUser targetUser) {
		// Users can access their own profile
		if (Objects.equals(currentUser.getId(), targetUser.getId())) {
			return true;
		}

		// Admins and super admins can access any profile
		return isAdminOrSuperAdmin(currentUser);
	}

	private boolean isAdminOrSuperAdmin(InptUser user) {
		return user.getRole() == Role.ROLE_ADMIN || user.getRole() == Role.ROLE_SUPER_ADMIN;
	}

	private boolean isSuperAdmin(InptUser user) {
		return user.getRole() == Role.ROLE_SUPER_ADMIN;
	}

	private void updateUserFields(InptUser user, ProfileUpdateRequestDto updateRequest) {
		// Update fields only if they are provided (not null) - partial updates allowed
		if (updateRequest.getFullName() != null) {
			String fullName = profileValidationUtils.sanitizeString(updateRequest.getFullName());

			if (!profileValidationUtils.isSafeInput(fullName)) {
				log.warn("Invalid full name - contains potentially dangerous content");
				throw new IllegalArgumentException("Full name contains invalid characters.");
			}

			if (fullName.isEmpty()) {
				log.warn("Invalid full name - cannot be empty");
				throw new IllegalArgumentException("Full name must not be empty.");
			}
			if (fullName.length() < 2) {
				log.warn("Invalid full name - too short: {}", fullName);
				throw new IllegalArgumentException("Full name must be at least 2 characters long.");
			}
			user.setFullName(fullName);
		}
		if (updateRequest.getMajor() != null) {
			user.setMajor(updateRequest.getMajor());
		}
		if (updateRequest.getPhoneNumber() != null) {
			String phoneNumber = updateRequest.getPhoneNumber().trim();
			// Phone number validation is already handled by DTO validation
			user.setPhoneNumber(phoneNumber);
		}
		if (updateRequest.getBirthDate() != null) {
			LocalDate birthDate = updateRequest.getBirthDate();
			if (birthDate.isAfter(LocalDate.now())) {
				log.warn("Invalid birth date - cannot be in the future: {}", birthDate);
				throw new IllegalArgumentException("Birth date cannot be in the future.");
			}
			if (birthDate.isBefore(LocalDate.of(1900, 1, 1))) {
				log.warn("Invalid birth date - too old: {}", birthDate);
				throw new IllegalArgumentException("Invalid birth date.");
			}
			user.setBirthDate(birthDate);
		}
		if (updateRequest.getCountry() != null) {
			String country = profileValidationUtils.sanitizeString(updateRequest.getCountry());
			if (!profileValidationUtils.isSafeInput(country)) {
				log.warn("Invalid country - contains potentially dangerous content");
				throw new IllegalArgumentException("Country contains invalid characters.");
			}
			user.setCountry(country);
		}
		if (updateRequest.getCity() != null) {
			String city = profileValidationUtils.sanitizeString(updateRequest.getCity());
			if (!profileValidationUtils.isSafeInput(city)) {
				log.warn("Invalid city - contains potentially dangerous content");
				throw new IllegalArgumentException("City contains invalid characters.");
			}
			user.setCity(city);
		}
		if (updateRequest.getLinkedinId() != null) {
			String linkedinId = updateRequest.getLinkedinId().trim();
			if (!profileValidationUtils.isSafeInput(linkedinId)) {
				log.warn("Invalid LinkedIn URL - contains potentially dangerous content");
				throw new IllegalArgumentException("LinkedIn URL contains invalid characters.");
			}
			user.setLinkedinId(linkedinId);
		}

	}
}
