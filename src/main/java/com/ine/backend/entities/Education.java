package com.ine.backend.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "educations")
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class Education {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotBlank(message = "Institution name is required")
	@Column(nullable = false)
	private String institutionName;

	@NotBlank(message = "Degree is required")
	@Column(nullable = false)
	private String degree;

	@NotBlank(message = "Field of study is required")
	@Column(nullable = false)
	private String fieldOfStudy;

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

	// Helper method to determine if it's current education
	public boolean isCurrent() {
		return endYear == null;
	}
}
