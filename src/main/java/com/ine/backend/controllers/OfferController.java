package com.ine.backend.controllers;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.ine.backend.dto.ApiResponseDto;
import com.ine.backend.dto.OfferRequestDto;
import com.ine.backend.dto.OfferResponseDto;
import com.ine.backend.entities.InptUser;
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
	public ResponseEntity<ApiResponseDto<List<OfferResponseDto>>> getOffers() {
		try {
			log.debug("Fetching all offers");
			List<OfferResponseDto> offers = offerService.getAllOffers();
			if (offers.isEmpty()) {
				log.info("No offers found - returning empty result");
			}
			return new ResponseEntity<>(ApiResponseDto.<List<OfferResponseDto>>builder().message("OK").response(offers)
					.isSuccess(true).build(), HttpStatus.OK);
		} catch (Exception e) {
			log.error("Failed to retrieve offers. Error: {}", e.getMessage(), e);
			return new ResponseEntity<>(ApiResponseDto.<List<OfferResponseDto>>builder().message(e.getMessage())
					.response(null).isSuccess(false).build(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	// Create an offer
	@PostMapping
	public ResponseEntity<ApiResponseDto<OfferResponseDto>> createOffer(
			@RequestBody @Valid OfferRequestDto requestDto) {
		try {
			log.info("Attempting to create offer with title: {}", requestDto != null ? requestDto.getTitle() : "null");
			OfferResponseDto created = offerService.createOffer(requestDto);
			log.info("Successfully created offer with id: {}", created != null ? created.getId() : null);
			return new ResponseEntity<>(ApiResponseDto.<OfferResponseDto>builder().message("Offer created")
					.response(created).isSuccess(true).build(), HttpStatus.CREATED);
		} catch (RuntimeException e) {
			// For domain/business errors (e.g., validation, conflicts), return 400 by
			// default
			log.warn("Business error while creating offer. Error: {}", e.getMessage(), e);
			return new ResponseEntity<>(ApiResponseDto.<OfferResponseDto>builder().message(e.getMessage())
					.response(null).isSuccess(false).build(), HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			log.error("Failed to create offer. Error: {}", e.getMessage(), e);
			return new ResponseEntity<>(ApiResponseDto.<OfferResponseDto>builder().message(e.getMessage())
					.response(null).isSuccess(false).build(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	// Apply to an offer
	@PostMapping("/{id}/apply")
	public ResponseEntity<ApiResponseDto<Void>> applyToOffer(@PathVariable("id") Long offerId,
			@RequestParam("userId") Long userId, @RequestBody(required = false) ApplicationRequest body) {
		try {
			log.info("User {} applying to offer {}", userId, offerId);
			InptUser user = userService.getUser(userId);
			String message = body != null ? body.getMessage() : null;
			String resumeUrl = body != null ? body.getResumeUrl() : null;
			offerService.applyToOffer(offerId, user, message, resumeUrl);
			log.info("User {} successfully applied to offer {}", userId, offerId);
			return new ResponseEntity<>(
					ApiResponseDto.<Void>builder().message("Applied").response(null).isSuccess(true).build(),
					HttpStatus.CREATED);
		} catch (RuntimeException e) {
			// Likely not found or business rule violation
			log.warn("Business error while applying to offer {} by user {}. Error: {}", offerId, userId, e.getMessage(),
					e);
			return new ResponseEntity<>(
					ApiResponseDto.<Void>builder().message(e.getMessage()).response(null).isSuccess(false).build(),
					HttpStatus.NOT_FOUND);
		} catch (Exception e) {
			log.error("Unexpected error while applying to offer {} by user {}. Error: {}", offerId, userId,
					e.getMessage(), e);
			return new ResponseEntity<>(
					ApiResponseDto.<Void>builder().message(e.getMessage()).response(null).isSuccess(false).build(),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@Data
	private static class ApplicationRequest {
		private String message;
		private String resumeUrl;
	}
}
