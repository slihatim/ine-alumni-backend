package com.ine.backend.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.ine.backend.dto.*;
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
	public ResponseEntity<PageResponseDTO<LaureatDTO>> getAllLaureats(
			@Parameter(description = "Page number (0-based)") @RequestParam(defaultValue = "0") int page,
			@Parameter(description = "Page size") @RequestParam(defaultValue = "12") int size,
			@Parameter(description = "Sort field (fullName, graduationYear, city, currentPosition)") @RequestParam(defaultValue = "fullName") String sortBy,
			@Parameter(description = "Sort direction (asc, desc)") @RequestParam(defaultValue = "asc") String sortDir) {

		PageResponseDTO<LaureatDTO> data = laureatService.getAllLaureats(page, size, sortBy, sortDir);
		return ResponseEntity.ok(data);
	}

	@GetMapping("/search")
	@Operation(summary = "Search alumni", description = "Search alumni by name, location, major, position, or company")
	public ResponseEntity<PageResponseDTO<LaureatDTO>> searchLaureats(
			@Parameter(description = "Search term") @RequestParam String q,
			@Parameter(description = "Page number (0-based)") @RequestParam(defaultValue = "0") int page,
			@Parameter(description = "Page size") @RequestParam(defaultValue = "12") int size) {

		PageResponseDTO<LaureatDTO> data = laureatService.searchLaureats(q, page, size);
		return ResponseEntity.ok(data);
	}

	@PostMapping("/filter")
	@Operation(summary = "Filter alumni with advanced criteria", description = "Filter alumni using multiple criteria such as major, company, graduation year, location, domain, and skills")
	public ResponseEntity<PageResponseDTO<LaureatDTO>> filterLaureats(@RequestBody LaureatFilterDTO filter,
			@Parameter(description = "Page number (0-based)") @RequestParam(defaultValue = "0") int page,
			@Parameter(description = "Page size") @RequestParam(defaultValue = "12") int size,
			@Parameter(description = "Sort field") @RequestParam(defaultValue = "fullName") String sortBy,
			@Parameter(description = "Sort direction") @RequestParam(defaultValue = "asc") String sortDir) {

		PageResponseDTO<LaureatDTO> data = laureatService.filterLaureats(filter, page, size, sortBy, sortDir);
		return ResponseEntity.ok(data);
	}

	@GetMapping("/{id}")
	@Operation(summary = "Get alumni details", description = "Get detailed profile information for a specific alumni including education, experience, skills, and external links")
	public ResponseEntity<LaureatDetailsDTO> getLaureatDetails(
			@Parameter(description = "Alumni ID") @PathVariable Long id) {

		LaureatDetailsDTO laureat = laureatService.getLaureatDetails(id);
		return ResponseEntity.ok(laureat);
	}

	@GetMapping("/filter-options/majors")
	@Operation(summary = "Get available majors for filtering", description = "Get list of all available majors that have alumni")
	public ResponseEntity<List<Major>> getAvailableMajors() {
		List<Major> majors = laureatService.getAvailableMajors();
		return ResponseEntity.ok(majors);
	}

	@GetMapping("/filter-options/graduation-years")
	@Operation(summary = "Get available graduation years for filtering", description = "Get list of all available graduation years")
	public ResponseEntity<List<Integer>> getAvailableGraduationYears() {
		List<Integer> years = laureatService.getAvailableGraduationYears();
		return ResponseEntity.ok(years);
	}
	@GetMapping("/filter-options/domains")
	@Operation(summary = "Get available domains for filtering", description = "Get list of all available work domains")
	public ResponseEntity<Domain[]> getAvailableDomains() {
		Domain[] domains = Domain.values();
		return ResponseEntity.ok(domains);
	}
}
