package com.ine.backend.services.impl;

import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ine.backend.dto.ChangeEmailRequestDto;
import com.ine.backend.dto.ChangePasswordRequestDto;
import com.ine.backend.dto.ProfileResponseDto;
import com.ine.backend.dto.ProfileUpdateRequestDto;
import com.ine.backend.entities.InptUser;
import com.ine.backend.entities.Role;
import com.ine.backend.exceptions.InvalidPasswordException;
import com.ine.backend.exceptions.ProfileNotFoundException;
import com.ine.backend.exceptions.UnauthorizedProfileAccessException;
import com.ine.backend.exceptions.UserAlreadyExistsException;
import com.ine.backend.mappers.ProfileMapper;
import com.ine.backend.repositories.UserRepository;
import com.ine.backend.services.ProfileService;

@Service
@Transactional
public class ProfileServiceImpl implements ProfileService {

	private static final Logger log = LoggerFactory.getLogger(ProfileServiceImpl.class);

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private ProfileMapper profileMapper;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Override
	@Transactional(readOnly = true)
	public ProfileResponseDto getCurrentUserProfile(String userEmail) {
		try {
			log.info("Getting profile for user: {}", userEmail);
			InptUser user = findUserByEmail(userEmail);
			return profileMapper.toProfileResponseDto(user);
		} catch (Exception e) {
			log.error("Error getting profile for user: {}: {}", userEmail, e.getMessage());
			throw e;
		}
	}

	@Override
	@Transactional(readOnly = true)
	public ProfileResponseDto getUserProfile(Long userId, String currentUserEmail) {
		try {
			log.info("Getting profile for user ID: {} by user: {}", userId, currentUserEmail);

			InptUser currentUser = findUserByEmail(currentUserEmail);
			InptUser targetUser = findUserById(userId);

			// Check if user can access this profile
			if (!canAccessProfile(currentUser, targetUser)) {
				throw new UnauthorizedProfileAccessException("Vous n'êtes pas autorisé à accéder à ce profil.");
			}

			return profileMapper.toProfileResponseDto(targetUser);
		} catch (Exception e) {
			log.error("Error getting profile for user ID: {} by user: {}: {}", userId, currentUserEmail,
					e.getMessage());
			throw e;
		}
	}

	@Override
	public ProfileResponseDto updateCurrentUserProfile(String userEmail, ProfileUpdateRequestDto updateRequest) {
		try {
			log.info("Updating profile for user: {}", userEmail);
			InptUser user = findUserByEmail(userEmail);

			updateUserFields(user, updateRequest);
			InptUser savedUser = userRepository.save(user);

			log.info("Profile updated successfully for user: {}", userEmail);
			return profileMapper.toProfileResponseDto(savedUser);
		} catch (Exception e) {
			log.error("Error updating profile for user: {}: {}", userEmail, e.getMessage());
			throw e;
		}
	}

	@Override
	public ProfileResponseDto updateUserProfile(Long userId, String currentUserEmail,
			ProfileUpdateRequestDto updateRequest) {
		try {
			log.info("Admin updating profile for user ID: {} by admin: {}", userId, currentUserEmail);

			InptUser currentUser = findUserByEmail(currentUserEmail);
			InptUser targetUser = findUserById(userId);

			// Only admins can update other users' profiles
			if (!isAdminOrSuperAdmin(currentUser)) {
				throw new UnauthorizedProfileAccessException(
						"Seuls les administrateurs peuvent modifier les profils d'autres utilisateurs.");
			}

			updateUserFields(targetUser, updateRequest);
			InptUser savedUser = userRepository.save(targetUser);

			log.info("Profile updated successfully for user ID: {} by admin: {}", userId, currentUserEmail);
			return profileMapper.toProfileResponseDto(savedUser);
		} catch (Exception e) {
			log.error("Error updating profile for user ID: {} by admin: {}: {}", userId, currentUserEmail,
					e.getMessage());
			throw e;
		}
	}

	@Override
	public String changeUserEmail(String userEmail, ChangeEmailRequestDto changeEmailRequest) {
		try {
			log.info("Changing email for user: {} to: {}", userEmail, changeEmailRequest.getNewEmail());

			InptUser user = findUserByEmail(userEmail);

			// Check if new email already exists
			InptUser existingUser = userRepository.findByEmail(changeEmailRequest.getNewEmail());
			if (existingUser != null) {
				throw new UserAlreadyExistsException("Cette adresse email est déjà utilisée.");
			}

			user.setEmail(changeEmailRequest.getNewEmail());
			user.setIsEmailVerified(false); // Require email verification for new email
			userRepository.save(user);

			log.info("Email changed successfully for user: {}", userEmail);
			return "L'adresse email a été modifiée avec succès. Veuillez vérifier votre nouvelle adresse email.";
		} catch (Exception e) {
			log.error("Error changing email for user: {} to: {}: {}", userEmail, changeEmailRequest.getNewEmail(),
					e.getMessage());
			throw e;
		}
	}

	@Override
	public String changeUserPassword(String userEmail, ChangePasswordRequestDto changePasswordRequest) {
		try {
			log.info("Changing password for user: {}", userEmail);

			InptUser user = findUserByEmail(userEmail);

			// Verify current password
			if (!passwordEncoder.matches(changePasswordRequest.getCurrentPassword(), user.getPassword())) {
				throw new InvalidPasswordException("Le mot de passe actuel est incorrect.");
			}

			// Verify password confirmation
			if (!changePasswordRequest.getNewPassword().equals(changePasswordRequest.getConfirmPassword())) {
				throw new InvalidPasswordException("La confirmation du mot de passe ne correspond pas.");
			}

			// Check if new password is different from current
			if (passwordEncoder.matches(changePasswordRequest.getNewPassword(), user.getPassword())) {
				throw new InvalidPasswordException("Le nouveau mot de passe doit être différent de l'ancien.");
			}

			user.setPassword(passwordEncoder.encode(changePasswordRequest.getNewPassword()));
			userRepository.save(user);

			log.info("Password changed successfully for user: {}", userEmail);
			return "Le mot de passe a été modifié avec succès.";
		} catch (Exception e) {
			log.error("Error changing password for user: {}: {}", userEmail, e.getMessage());
			throw e;
		}
	}

	@Override
	public String deactivateAccount(String userEmail) {
		try {
			log.info("Deactivating account for user: {}", userEmail);

			InptUser user = findUserByEmail(userEmail);
			user.setIsAccountVerified(false);
			userRepository.save(user);

			log.info("Account deactivated successfully for user: {}", userEmail);
			return "Le compte a été désactivé avec succès.";
		} catch (Exception e) {
			log.error("Error deactivating account for user: {}: {}", userEmail, e.getMessage());
			throw e;
		}
	}

	@Override
	public String deleteUserAccount(Long userId, String currentUserEmail) {
		try {
			log.info("Admin deleting user account ID: {} by admin: {}", userId, currentUserEmail);

			InptUser currentUser = findUserByEmail(currentUserEmail);
			InptUser targetUser = findUserById(userId);

			// Only super admins can delete accounts
			if (!isSuperAdmin(currentUser)) {
				throw new UnauthorizedProfileAccessException(
						"Seuls les super administrateurs peuvent supprimer des comptes utilisateur.");
			}

			// Prevent self-deletion
			if (Objects.equals(currentUser.getId(), targetUser.getId())) {
				throw new UnauthorizedProfileAccessException("Vous ne pouvez pas supprimer votre propre compte.");
			}

			userRepository.delete(targetUser);

			log.info("User account deleted successfully ID: {} by admin: {}", userId, currentUserEmail);
			return "Le compte utilisateur a été supprimé avec succès.";
		} catch (Exception e) {
			log.error("Error deleting user account ID: {} by admin: {}: {}", userId, currentUserEmail, e.getMessage());
			throw e;
		}
	}

	// Helper methods
	private InptUser findUserByEmail(String email) {
		InptUser user = userRepository.findByEmail(email);
		if (user == null) {
			throw new ProfileNotFoundException("Utilisateur non trouvé avec l'email: " + email);
		}
		return user;
	}

	private InptUser findUserById(Long id) {
		return userRepository.findById(id)
				.orElseThrow(() -> new ProfileNotFoundException("Utilisateur non trouvé avec l'ID: " + id));
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
			if (updateRequest.getFullName().trim().isEmpty()) {
				throw new IllegalArgumentException("Le nom complet ne doit pas être vide.");
			}
			user.setFullName(updateRequest.getFullName().trim());
		}
		if (updateRequest.getMajor() != null) {
			user.setMajor(updateRequest.getMajor());
		}
		if (updateRequest.getGraduationYear() != null) {
			user.setGraduationYear(updateRequest.getGraduationYear());
		}
		if (updateRequest.getPhoneNumber() != null) {
			user.setPhoneNumber(updateRequest.getPhoneNumber().trim());
		}
		if (updateRequest.getBirthDate() != null) {
			user.setBirthDate(updateRequest.getBirthDate());
		}
		if (updateRequest.getGender() != null) {
			user.setGender(updateRequest.getGender());
		}
		if (updateRequest.getCountry() != null) {
			user.setCountry(updateRequest.getCountry().trim());
		}
		if (updateRequest.getCity() != null) {
			user.setCity(updateRequest.getCity().trim());
		}
		if (updateRequest.getLinkedinId() != null) {
			user.setLinkedinId(updateRequest.getLinkedinId().trim());
		}
	}
}
