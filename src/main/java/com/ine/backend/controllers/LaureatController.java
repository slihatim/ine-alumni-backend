package com.ine.backend.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.ine.backend.dto.*;
import com.ine.backend.dto.ApiResponseDto;
import com.ine.backend.entities.Domain;
import com.ine.backend.entities.Major;
import com.ine.backend.services.LaureatService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/v1/laureats")
@Tag(name = "Alumni", description = "Alumni management endpoints")
public class LaureatController {

	@Autowired
	private LaureatService laureatService;

	@GetMapping
	@Operation(summary = "Get all verified alumni", description = "Retrieve paginated list of all verified alumni")
	public ResponseEntity<ApiResponseDto<PageResponseDTO<LaureatDTO>>> getAllLaureats(
			@Parameter(description = "Page number (0-based)") @RequestParam(defaultValue = "0") int page,
			@Parameter(description = "Page size") @RequestParam(defaultValue = "12") int size,
			@Parameter(description = "Sort field (fullName, graduationYear, city, currentPosition)") @RequestParam(defaultValue = "fullName") String sortBy,
			@Parameter(description = "Sort direction (asc, desc)") @RequestParam(defaultValue = "asc") String sortDir) {

		PageResponseDTO<LaureatDTO> data = laureatService.getAllLaureats(page, size, sortBy, sortDir);
		ApiResponseDto<PageResponseDTO<LaureatDTO>> response = ApiResponseDto.<PageResponseDTO<LaureatDTO>>builder()
				.isSuccess(true).message("Alumni retrieved successfully").response(data).build();
		return ResponseEntity.ok(response);
	}

	@GetMapping("/search")
	@Operation(summary = "Search alumni", description = "Search alumni by name, location, major, position, or company")
	public ResponseEntity<ApiResponseDto<PageResponseDTO<LaureatDTO>>> searchLaureats(
			@Parameter(description = "Search term") @RequestParam String q,
			@Parameter(description = "Page number (0-based)") @RequestParam(defaultValue = "0") int page,
			@Parameter(description = "Page size") @RequestParam(defaultValue = "12") int size) {

		PageResponseDTO<LaureatDTO> data = laureatService.searchLaureats(q, page, size);
		ApiResponseDto<PageResponseDTO<LaureatDTO>> response = ApiResponseDto.<PageResponseDTO<LaureatDTO>>builder()
				.isSuccess(true).message("Alumni search results").response(data).build();
		return ResponseEntity.ok(response);
	}

	@PostMapping("/filter")
	@Operation(summary = "Filter alumni with advanced criteria", description = "Filter alumni using multiple criteria such as major, company, graduation year, location, domain, and skills")
	public ResponseEntity<ApiResponseDto<PageResponseDTO<LaureatDTO>>> filterLaureats(
			@RequestBody LaureatFilterDTO filter,
			@Parameter(description = "Page number (0-based)") @RequestParam(defaultValue = "0") int page,
			@Parameter(description = "Page size") @RequestParam(defaultValue = "12") int size,
			@Parameter(description = "Sort field") @RequestParam(defaultValue = "fullName") String sortBy,
			@Parameter(description = "Sort direction") @RequestParam(defaultValue = "asc") String sortDir) {

		PageResponseDTO<LaureatDTO> data = laureatService.filterLaureats(filter, page, size, sortBy, sortDir);
		ApiResponseDto<PageResponseDTO<LaureatDTO>> response = ApiResponseDto.<PageResponseDTO<LaureatDTO>>builder()
				.isSuccess(true).message("Filtered alumni retrieved").response(data).build();
		return ResponseEntity.ok(response);
	}

	@GetMapping("/{id}")
	@Operation(summary = "Get alumni details", description = "Get detailed profile information for a specific alumni including education, experience, skills, and external links")
	public ResponseEntity<ApiResponseDto<LaureatDetailsDTO>> getLaureatDetails(
			@Parameter(description = "Alumni ID") @PathVariable Long id) {

		LaureatDetailsDTO laureat = laureatService.getLaureatDetails(id);
		ApiResponseDto<LaureatDetailsDTO> response = ApiResponseDto.<LaureatDetailsDTO>builder().isSuccess(true)
				.message("Alumni details retrieved").response(laureat).build();
		return ResponseEntity.ok(response);
	}

	@GetMapping("/filter-options/majors")
	@Operation(summary = "Get available majors for filtering", description = "Get list of all available majors that have alumni")
	public ResponseEntity<ApiResponseDto<List<Major>>> getAvailableMajors() {
		List<Major> majors = laureatService.getAvailableMajors();
		ApiResponseDto<List<Major>> response = ApiResponseDto.<List<Major>>builder().isSuccess(true)
				.message("Available majors retrieved").response(majors).build();
		return ResponseEntity.ok(response);
	}

	@GetMapping("/filter-options/graduation-years")
	@Operation(summary = "Get available graduation years for filtering", description = "Get list of all available graduation years")
	public ResponseEntity<ApiResponseDto<List<Integer>>> getAvailableGraduationYears() {
		List<Integer> years = laureatService.getAvailableGraduationYears();
		ApiResponseDto<List<Integer>> response = ApiResponseDto.<List<Integer>>builder().isSuccess(true)
				.message("Available graduation years retrieved").response(years).build();
		return ResponseEntity.ok(response);
	}
	@GetMapping("/filter-options/domains")
	@Operation(summary = "Get available domains for filtering", description = "Get list of all available work domains")
	public ResponseEntity<ApiResponseDto<Domain[]>> getAvailableDomains() {
		Domain[] domains = Domain.values();
		ApiResponseDto<Domain[]> response = ApiResponseDto.<Domain[]>builder().isSuccess(true)
				.message("Available domains retrieved").response(domains).build();
		return ResponseEntity.ok(response);
	}
}
