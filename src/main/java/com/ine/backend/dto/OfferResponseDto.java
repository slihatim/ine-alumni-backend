package com.ine.backend.dto;

import java.time.LocalDateTime;

import com.ine.backend.entities.OfferType;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OfferResponseDto {
	private Long id;
	private String title;
	private String company;
	private String location;
	private OfferType type;
	private String customType;
	private String duration;
	private String description;
	private String link;
	private LocalDateTime postedAt;
}
