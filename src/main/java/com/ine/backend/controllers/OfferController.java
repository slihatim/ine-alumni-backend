package com.ine.backend.controllers;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.ine.backend.dto.OfferRequestDto;
import com.ine.backend.dto.OfferResponseDto;
import com.ine.backend.entities.InptUser;
import com.ine.backend.entities.OfferType;
import com.ine.backend.services.OfferService;
import com.ine.backend.services.UserService;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Data;

@RestController
@RequestMapping("/api/v1/offers")
@AllArgsConstructor
public class OfferController {

	private static final Logger log = LoggerFactory.getLogger(OfferController.class);

	private final OfferService offerService;
	private final UserService userService;

	// Get all offers
	@GetMapping
	public ResponseEntity<List<OfferResponseDto>> getOffers() {
		log.debug("Fetching all offers");
		List<OfferResponseDto> offers = offerService.getAllOffers();
		if (offers.isEmpty()) {
			log.info("No offers found - returning empty result");
		}
		return ResponseEntity.ok(offers);
	}

	// Create an offer
	@PostMapping
	public ResponseEntity<OfferResponseDto> createOffer(@RequestBody @Valid OfferRequestDto requestDto) {
		log.info("Attempting to create offer with title: {}", requestDto != null ? requestDto.getTitle() : "null");
		OfferResponseDto created = offerService.createOffer(requestDto);
		log.info("Successfully created offer with id: {}", created != null ? created.getId() : null);
		return ResponseEntity.status(HttpStatus.CREATED).body(created);
	}

	// Get single offer by id
	@GetMapping("/{id}")
	public ResponseEntity<OfferResponseDto> getOffer(@PathVariable("id") Long id) {
		OfferResponseDto dto = offerService.getOfferById(id);
		return ResponseEntity.ok(dto);
	}

	// Update an offer
	@PutMapping("/{id}")
	public ResponseEntity<OfferResponseDto> updateOffer(@PathVariable("id") Long id,
			@RequestBody @Valid OfferRequestDto requestDto) {
		OfferResponseDto updated = offerService.updateOffer(id, requestDto);
		return ResponseEntity.ok(updated);
	}

	// Delete an offer
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteOffer(@PathVariable("id") Long id) {
		offerService.deleteOffer(id);
		return ResponseEntity.noContent().build();
	}

	// Get offers by company
	@GetMapping("/company/{company}")
	public ResponseEntity<List<OfferResponseDto>> getByCompany(@PathVariable("company") String company) {
		List<OfferResponseDto> offers = offerService.getOffersByCompany(company);
		return ResponseEntity.ok(offers);
	}

	// Get offers by location
	@GetMapping("/location/{location}")
	public ResponseEntity<List<OfferResponseDto>> getByLocation(@PathVariable("location") String location) {
		List<OfferResponseDto> offers = offerService.getOffersByLocation(location);
		return ResponseEntity.ok(offers);
	}

	// Get offers by type (path variable as string, convert to enum)
	@GetMapping("/type/{type}")
	public ResponseEntity<List<OfferResponseDto>> getByType(@PathVariable("type") String type) {
		OfferType offerType = OfferType.fromString(type);
		List<OfferResponseDto> offers = offerService.getOffersByType(offerType);
		return ResponseEntity.ok(offers);
	}

	// Apply to an offer
	@PostMapping("/{id}/apply")
	public ResponseEntity<Void> applyToOffer(@PathVariable("id") Long offerId, @RequestParam("userId") Long userId,
			@RequestBody(required = false) ApplicationRequest body) {
		log.info("User {} applying to offer {}", userId, offerId);
		InptUser user = userService.getUser(userId);
		String message = body != null ? body.getMessage() : null;
		String resumeUrl = body != null ? body.getResumeUrl() : null;
		offerService.applyToOffer(offerId, user, message, resumeUrl);
		log.info("User {} successfully applied to offer {}", userId, offerId);
		return ResponseEntity.status(HttpStatus.CREATED).build();
	}

	// Get appliers (applicant IDs) for an offer
	@GetMapping("/{id}/appliers")
	public ResponseEntity<List<Long>> getOfferAppliers(@PathVariable("id") Long offerId) {
		List<Long> appliers = offerService.getOfferAppliers(offerId);
		return ResponseEntity.ok(appliers);
	}

	@Data
	private static class ApplicationRequest {
		private String message;
		private String resumeUrl;
	}
}
