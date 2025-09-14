package com.ine.backend.entities;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Entity
@Table(name = "offers", indexes = {@Index(name = "idx_offer_title", columnList = "title"),
		@Index(name = "idx_offer_company", columnList = "company"),
		@Index(name = "idx_offer_location", columnList = "location")})
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Offer {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotBlank
	@Column(nullable = false)
	private String title;

	@NotBlank
	@Column(nullable = false)
	private String company;

	@NotBlank
	@Column(nullable = false)
	private String location;

	// FIXED: Changed from entity relationship to enum for type safety as requested
	// in PR
	@Enumerated(EnumType.STRING)
	@Column(name = "type", nullable = false)
	private OfferType type;

	// ADDED: Custom type field for when type is OTHER, replacing the OfferType
	// entity's customType
	@Column(length = 100)
	private String customType;

	@Column(length = 2048)
	private String description;

	private String duration;

	private String link;

	@CreationTimestamp
	@Column(updatable = false)
	private LocalDateTime createdAt;

	@UpdateTimestamp
	private LocalDateTime updatedAt;

	@OneToMany(mappedBy = "offer", cascade = CascadeType.ALL, orphanRemoval = true)
	@Builder.Default
	@JsonIgnore
	private List<OfferApplication> applications = new ArrayList<>();
}
