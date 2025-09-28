package com.ine.backend.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "external_links")
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class ExternalLink {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotBlank(message = "Link title is required")
	@Column(nullable = false)
	private String title;

	@NotBlank(message = "URL is required")
	@Column(nullable = false)
	private String url;

	@Enumerated(EnumType.STRING)
	private LinkType linkType;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "laureat_id")
	private Laureat laureat;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "company_id")
	private Company company;
}
