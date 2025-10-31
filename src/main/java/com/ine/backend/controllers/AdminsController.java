package com.ine.backend.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.ine.backend.dto.AdminCreationRequestDto;
import com.ine.backend.dto.ApiResponseDto;
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
	public ResponseEntity<ApiResponseDto<String>> createAdmin(@RequestBody @Valid AdminCreationRequestDto requestDto)
			throws UserAlreadyExistsException {

		adminsService.createAdmin(requestDto);

		return ResponseEntity.status(HttpStatus.CREATED)
				.body(new ApiResponseDto<>("Le compte admin a été créé avec succès !", null, true));
	}

	@GetMapping
	@PreAuthorize("hasAuthority('admin:read')")
	public ResponseEntity<ApiResponseDto<List<Admin>>> listAdmins() {
		List<Admin> admins = adminsService.getAllAdmins();
		return ResponseEntity.ok(new ApiResponseDto<>("Liste des admins", admins, true));
	}

	@GetMapping("/{id}")
	@PreAuthorize("hasAuthority('admin:read')")
	public ResponseEntity<ApiResponseDto<Admin>> getAdmin(@PathVariable Long id) {
		Admin admin = adminsService.getAdminById(id);
		return ResponseEntity.ok(new ApiResponseDto<>("Admin trouvé", admin, true));
	}

	@PutMapping("/{id}")
	@PreAuthorize("hasAuthority('admin:update')")
	public ResponseEntity<ApiResponseDto<Admin>> updateAdmin(@PathVariable Long id,
			@RequestBody @Valid AdminCreationRequestDto requestDto) throws UserAlreadyExistsException {
		Admin updated = adminsService.updateAdmin(id, requestDto);
		return ResponseEntity.ok(new ApiResponseDto<>("Admin mis à jour", updated, true));
	}

	@DeleteMapping("/{id}")
	@PreAuthorize("hasAuthority('admin:delete')")
	public ResponseEntity<ApiResponseDto<String>> deleteAdmin(@PathVariable Long id) {
		adminsService.deleteAdmin(id);
		return ResponseEntity.ok(new ApiResponseDto<>("Admin supprimé", null, true));
	}
}
