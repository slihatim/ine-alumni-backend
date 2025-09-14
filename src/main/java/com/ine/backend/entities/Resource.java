package com.ine.backend.entities;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Entity
@Table(name = "resources")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class Resource {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotBlank(message = "Title is required")
	@Column(nullable = false)
	private String title;

	@Column(columnDefinition = "TEXT")
	private String description;

	@NotNull(message = "IsConfidential is required")
	@Column(nullable = false)
	private Boolean isConfidential;

	@NotNull(message = "Category is required")
	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private Category category; // formations, podcasts, documents, videos, etc.

	@NotBlank(message = "Link is required")
	@Column(nullable = false)
	private String link;

	@NotNull(message = "Author is required")
	private String author;

	// The field can be true in case we want to update a certain row
	@CreationTimestamp
	@Column(updatable = false)
	private LocalDateTime createdDate;

	@NotNull(message = "Domain is required")
	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private Domain domain;

	// WE MIGHT THINK LATER ABOUT ADDING THIS TWO FIELDS FOR EVERY ENTITY TO ENSURE
	// PROPER LOGGING
	// @Column(name = "created_at")
	// private LocalDateTime createdAt;
	//
	// @Column(name = "updated_at")
	// private LocalDateTime updatedAt;
}
