package com.ine.backend.dto;

import com.ine.backend.entities.OfferType;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OfferRequestDto {

	@NotBlank
	private String title;

	@NotBlank
	private String company;

	@NotBlank
	private String location;

	@NotNull(message = "Offer type is required")
	private OfferType type;

	private String customType;

	private String duration;

	@NotBlank
	private String description;

	private String link;
}
