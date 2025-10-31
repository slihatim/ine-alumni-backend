package com.ine.backend.entities;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Entity
@DiscriminatorValue("laureat")
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@SuperBuilder
// Laureat users
public class Laureat extends InptUser {
	@ManyToOne
	@JoinColumn(name = "current_company_id")
	private Company currentCompany;

	@Column(columnDefinition = "TEXT")
	private String bio;

	private String currentPosition;
	private String profilePicture;

	@OneToMany(mappedBy = "laureat", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private List<Education> educations = new ArrayList<>();

	@OneToMany(mappedBy = "laureat", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private List<Experience> experiences = new ArrayList<>();

	@OneToMany(mappedBy = "laureat", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private List<Skill> skills = new ArrayList<>();

	@OneToMany(mappedBy = "laureat", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private List<ExternalLink> externalLinks = new ArrayList<>();

	@OneToMany(mappedBy = "laureat", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private List<CompanyReview> companyReviews = new ArrayList<>();
}
