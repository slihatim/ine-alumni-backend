package com.ine.backend.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.ine.backend.dto.*;
import com.ine.backend.dto.ApiResponseDto;
import com.ine.backend.services.CompanyService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/v1/companies")
@Tag(name = "Companies", description = "Company management endpoints")
public class CompanyController {

	@Autowired
	private CompanyService companyService;

	@GetMapping
	@Operation(summary = "Get all companies with alumni count", description = "Retrieve paginated list of companies that have alumni working in them")
	public ResponseEntity<ApiResponseDto<PageResponseDTO<CompanyDTO>>> getAllCompanies(
			@Parameter(description = "Page number (0-based)") @RequestParam(defaultValue = "0") int page,
			@Parameter(description = "Page size") @RequestParam(defaultValue = "12") int size,
			@Parameter(description = "Sort field (name, alumniCount, industry, location)") @RequestParam(defaultValue = "name") String sortBy,
			@Parameter(description = "Sort direction (asc, desc)") @RequestParam(defaultValue = "asc") String sortDir) {

		PageResponseDTO<CompanyDTO> data = companyService.getAllCompaniesWithAlumni(page, size, sortBy, sortDir);
		ApiResponseDto<PageResponseDTO<CompanyDTO>> response = ApiResponseDto.<PageResponseDTO<CompanyDTO>>builder()
				.isSuccess(true).message("Companies retrieved successfully").response(data).build();
		return ResponseEntity.ok(response);
	}

	@GetMapping("/search")
	@Operation(summary = "Search companies", description = "Search companies by name, industry, or location")
	public ResponseEntity<ApiResponseDto<PageResponseDTO<CompanyDTO>>> searchCompanies(
			@Parameter(description = "Search term") @RequestParam String q,
			@Parameter(description = "Page number (0-based)") @RequestParam(defaultValue = "0") int page,
			@Parameter(description = "Page size") @RequestParam(defaultValue = "12") int size) {

		PageResponseDTO<CompanyDTO> data = companyService.searchCompanies(q, page, size);
		ApiResponseDto<PageResponseDTO<CompanyDTO>> response = ApiResponseDto.<PageResponseDTO<CompanyDTO>>builder()
				.isSuccess(true).message("Companies search results").response(data).build();
		return ResponseEntity.ok(response);
	}

	@GetMapping("/{id}")
	@Operation(summary = "Get company details", description = "Get detailed information about a specific company including alumni and reviews")
	public ResponseEntity<ApiResponseDto<CompanyDetailsDTO>> getCompanyDetails(
			@Parameter(description = "Company ID") @PathVariable Long id) {

		CompanyDetailsDTO company = companyService.getCompanyDetails(id);
		ApiResponseDto<CompanyDetailsDTO> response = ApiResponseDto.<CompanyDetailsDTO>builder().isSuccess(true)
				.message("Company details retrieved").response(company).build();
		return ResponseEntity.ok(response);
	}

	@GetMapping("/{id}/alumni")
	@Operation(summary = "Get company alumni", description = "Get paginated list of alumni working at a specific company")
	public ResponseEntity<ApiResponseDto<PageResponseDTO<LaureatDTO>>> getCompanyAlumni(
			@Parameter(description = "Company ID") @PathVariable Long id,
			@Parameter(description = "Page number (0-based)") @RequestParam(defaultValue = "0") int page,
			@Parameter(description = "Page size") @RequestParam(defaultValue = "12") int size,
			@Parameter(description = "Sort field") @RequestParam(defaultValue = "fullName") String sortBy,
			@Parameter(description = "Sort direction") @RequestParam(defaultValue = "asc") String sortDir) {

		PageResponseDTO<LaureatDTO> data = companyService.getCompanyAlumni(id, page, size, sortBy, sortDir);
		ApiResponseDto<PageResponseDTO<LaureatDTO>> response = ApiResponseDto.<PageResponseDTO<LaureatDTO>>builder()
				.isSuccess(true).message("Company alumni retrieved").response(data).build();
		return ResponseEntity.ok(response);
	}

	@GetMapping("/{id}/reviews")
	@Operation(summary = "Get company reviews", description = "Get paginated list of reviews for a specific company")
	public ResponseEntity<ApiResponseDto<PageResponseDTO<CompanyReviewDTO>>> getCompanyReviews(
			@Parameter(description = "Company ID") @PathVariable Long id,
			@Parameter(description = "Page number (0-based)") @RequestParam(defaultValue = "0") int page,
			@Parameter(description = "Page size") @RequestParam(defaultValue = "10") int size) {

		PageResponseDTO<CompanyReviewDTO> data = companyService.getCompanyReviews(id, page, size);
		ApiResponseDto<PageResponseDTO<CompanyReviewDTO>> response = ApiResponseDto
				.<PageResponseDTO<CompanyReviewDTO>>builder().isSuccess(true).message("Company reviews retrieved")
				.response(data).build();
		return ResponseEntity.ok(response);
	}
}
