package com.ine.backend.entities;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "offer_applications", uniqueConstraints = @UniqueConstraint(name = "uk_offer_applicant", columnNames = {
		"offer_id", "applicant_id"}))
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OfferApplication {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "offer_id", nullable = false)
	@JsonIgnore
	private Offer offer;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "applicant_id", nullable = false)
	private InptUser applicant;

	@CreationTimestamp
	@Column(updatable = false)
	private LocalDateTime appliedAt;

	@Column(length = 2048)
	private String message;

	private String resumeUrl;
}
