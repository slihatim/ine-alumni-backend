package com.ine.backend.services;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ine.backend.dto.AdminCreationRequestDto;
import com.ine.backend.entities.Admin;
import com.ine.backend.exceptions.UserAlreadyExistsException;
import com.ine.backend.repositories.AdminRepository;

import lombok.AllArgsConstructor;

@Service
@Transactional
@AllArgsConstructor
public class AdminsService {

	private static final Logger log = LoggerFactory.getLogger(AdminsService.class);

	private AdminRepository adminRepository;
	private UserService userService;
	private PasswordEncoder passwordEncoder;

	/**
	 * Check if email exists in the system (across both admin and user repositories)
	 */
	private boolean emailExistsInSystem(String email) {
		return adminRepository.existsByEmail(email) || userService.existsByEmail(email);
	}

	public void createAdmin(AdminCreationRequestDto requestDto) throws UserAlreadyExistsException {
		log.info("Creating admin with email: {}", requestDto.getEmail());

		// Check email uniqueness across ALL user types (admins + regular users)
		if (emailExistsInSystem(requestDto.getEmail())) {
			log.warn("Admin creation failed - email already exists: {}", requestDto.getEmail());
			throw new UserAlreadyExistsException(
					"Admin creation failed: The provided email already exists. Please sign in or use a different email.");
		}

		Admin admin = new Admin();

		admin.setFullName(requestDto.getFullName());
		admin.setEmail(requestDto.getEmail());
		admin.setPassword(passwordEncoder.encode(requestDto.getPassword()));
		adminRepository.save(admin);

		log.info("Admin created successfully with id: {}", admin.getId());
	}

	// List all admins
	@Transactional(readOnly = true)
	public List<Admin> getAllAdmins() {
		log.info("Fetching all admins");
		return adminRepository.findAll();
	}

	// Get a single admin by id
	@Transactional(readOnly = true)
	public Admin getAdminById(Long id) {
		log.info("Fetching admin with id: {}", id);
		return adminRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("Admin not found with id: " + id));
	}

	// Update admin (partial update: only non-null / non-empty fields from DTO are
	// applied)
	public Admin updateAdmin(Long id, AdminCreationRequestDto requestDto) throws UserAlreadyExistsException {
		log.info("Updating admin with id: {}", id);

		Admin admin = getAdminById(id);

		if (requestDto.getFullName() != null && !requestDto.getFullName().isBlank()) {
			admin.setFullName(requestDto.getFullName());
		}

		if (requestDto.getEmail() != null && !requestDto.getEmail().isBlank()) {
			// Only check uniqueness if email is being changed
			if (!admin.getEmail().equalsIgnoreCase(requestDto.getEmail())) {
				log.info("Email change requested for admin id: {} from {} to {}", id, admin.getEmail(),
						requestDto.getEmail());

				// Check if new email already exists in the system
				if (emailExistsInSystem(requestDto.getEmail())) {
					log.warn("Admin update failed - email already exists: {}", requestDto.getEmail());
					throw new UserAlreadyExistsException("This email is already in use in the system.");
				}

				admin.setEmail(requestDto.getEmail());
			}
		}

		if (requestDto.getPassword() != null && !requestDto.getPassword().isBlank()) {
			log.info("Password update requested for admin id: {}", id);
			admin.setPassword(passwordEncoder.encode(requestDto.getPassword()));
		}

		Admin savedAdmin = adminRepository.save(admin);
		log.info("Admin updated successfully with id: {}", id);

		return savedAdmin;
	}

	// Delete admin by id
	public void deleteAdmin(Long id) {
		log.info("Deleting admin with id: {}", id);

		if (!adminRepository.existsById(id)) {
			log.warn("Admin deletion failed - admin not found with id: {}", id);
			throw new IllegalArgumentException("Admin not found with id: " + id);
		}

		adminRepository.deleteById(id);
		log.info("Admin deleted successfully with id: {}", id);
	}

	// Helper: find admin by email (returns null if not found to match repository
	// usage elsewhere)
	@Transactional(readOnly = true)
	public Admin findByEmail(String email) {
		log.debug("Looking up admin by email: {}", email);
		return adminRepository.findByEmail(email);
	}
}
