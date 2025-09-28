package com.ine.backend.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Experience entity to store work experiences
 */
@Entity
@Table(name = "experiences")
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class Experience {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotBlank(message = "Job title is required")
	@Column(nullable = false)
	private String jobTitle;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "company_id", nullable = false)
	private Company company;

	@Column(nullable = false)
	private String location;

	@Enumerated(EnumType.STRING)
	private Domain domain;

	@Min(value = 1960, message = "Start year must be greater than 1960")
	@Max(value = 2025, message = "Start year must be less than 2025")
	private Integer startYear;

	@Min(value = 1960, message = "End year must be greater than 1960")
	@Max(value = 2100, message = "End year must be less than 2100")
	private Integer endYear;

	@Column(columnDefinition = "TEXT")
	private String description;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "laureat_id", nullable = false)
	private Laureat laureat;

	// Helper method to determine if it's current position
	public boolean isCurrent() {
		return endYear == null;
	}
}
