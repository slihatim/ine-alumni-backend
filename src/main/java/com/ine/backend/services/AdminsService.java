package com.ine.backend.services;

import java.util.List;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.ine.backend.dto.AdminCreationRequestDto;
import com.ine.backend.entities.Admin;
import com.ine.backend.exceptions.UserAlreadyExistsException;
import com.ine.backend.repositories.AdminRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class AdminsService {
	private AdminRepository adminRepository;
	private PasswordEncoder passwordEncoder;

	public void createAdmin(AdminCreationRequestDto requestDto) throws UserAlreadyExistsException {
		if (adminRepository.existsByEmail(requestDto.getEmail())) {
			throw new UserAlreadyExistsException(
					"Échec de creation d'admin : l'email fourni existe déjà. Essayez de vous connecter ou utilisez un autre email.");
		}

		Admin admin = new Admin();

		admin.setFullName(requestDto.getFullName());
		admin.setEmail(requestDto.getEmail());
		admin.setPassword(passwordEncoder.encode(requestDto.getPassword()));
		adminRepository.save(admin);
	}

	// List all admins
	public List<Admin> getAllAdmins() {
		return adminRepository.findAll();
	}

	// Get a single admin by id
	public Admin getAdminById(Long id) {
		return adminRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("Admin non trouvé avec id: " + id));
	}

	// Update admin (partial update: only non-null / non-empty fields from DTO are
	// applied)
	public Admin updateAdmin(Long id, AdminCreationRequestDto requestDto) {
		Admin admin = getAdminById(id);

		if (requestDto.getFullName() != null && !requestDto.getFullName().isBlank()) {
			admin.setFullName(requestDto.getFullName());
		}
		if (requestDto.getEmail() != null && !requestDto.getEmail().isBlank()) {
			admin.setEmail(requestDto.getEmail());
		}
		if (requestDto.getPassword() != null && !requestDto.getPassword().isBlank()) {
			admin.setPassword(passwordEncoder.encode(requestDto.getPassword()));
		}

		return adminRepository.save(admin);
	}

	// Delete admin by id
	public void deleteAdmin(Long id) {
		if (!adminRepository.existsById(id)) {
			throw new IllegalArgumentException("Admin non trouvé avec id: " + id);
		}
		adminRepository.deleteById(id);
	}

	// Helper: find admin by email (returns null if not found to match repository
	// usage elsewhere)
	public Admin findByEmail(String email) {
		return adminRepository.findByEmail(email);
	}
}
