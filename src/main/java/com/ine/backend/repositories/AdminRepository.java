package com.ine.backend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ine.backend.entities.Admin;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

@Repository
public interface AdminRepository extends JpaRepository<Admin, Long> {
	Admin findByEmail(
			@Email(message = "L'adresse email n'est pas valide.") @NotBlank(message = "L'adresse email est obligatoire.") String email);

	boolean existsByEmail(
			@Email(message = "L'adresse email n'est pas valide.") @NotBlank(message = "L'adresse email est obligatoire.") String email);
}
