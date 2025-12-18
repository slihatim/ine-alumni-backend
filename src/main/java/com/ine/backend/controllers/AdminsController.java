package com.ine.backend.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.ine.backend.dto.AdminCreationRequestDto;
import com.ine.backend.entities.Admin;
import com.ine.backend.exceptions.UserAlreadyExistsException;
import com.ine.backend.services.AdminsService;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/v1/admins")
@AllArgsConstructor
public class AdminsController {
	private AdminsService adminsService;

	@PostMapping
	@PreAuthorize("hasAuthority('admin:create')")
	public ResponseEntity<String> createAdmin(@RequestBody @Valid AdminCreationRequestDto requestDto)
			throws UserAlreadyExistsException {

		adminsService.createAdmin(requestDto);

		return ResponseEntity.status(HttpStatus.CREATED).body("Le compte admin a été créé avec succès !");
	}

	@GetMapping
	@PreAuthorize("hasAuthority('admin:read')")
	public ResponseEntity<List<Admin>> listAdmins() {
		List<Admin> admins = adminsService.getAllAdmins();
		return ResponseEntity.ok(admins);
	}

	@GetMapping("/{id}")
	@PreAuthorize("hasAuthority('admin:read')")
	public ResponseEntity<Admin> getAdmin(@PathVariable Long id) {
		Admin admin = adminsService.getAdminById(id);
		return ResponseEntity.ok(admin);
	}

	@PutMapping("/{id}")
	@PreAuthorize("hasAuthority('admin:update')")
	public ResponseEntity<Admin> updateAdmin(@PathVariable Long id,
			@RequestBody @Valid AdminCreationRequestDto requestDto) throws UserAlreadyExistsException {
		Admin updated = adminsService.updateAdmin(id, requestDto);
		return ResponseEntity.ok(updated);
	}

	@DeleteMapping("/{id}")
	@PreAuthorize("hasAuthority('admin:delete')")
	public ResponseEntity<String> deleteAdmin(@PathVariable Long id) {
		adminsService.deleteAdmin(id);
		return ResponseEntity.ok("Admin supprimé");
	}
}
