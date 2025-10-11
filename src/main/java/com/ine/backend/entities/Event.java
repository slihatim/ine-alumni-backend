package com.ine.backend.entities;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "events")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Event {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotBlank(message = "Title is required")
	@Size(max = 255, message = "Title must not exceed 255 characters")
	@Column(nullable = false)
	private String title;

	@NotBlank(message = "Description is required")
	@Size(max = 1000, message = "Description must not exceed 1000 characters")
	@Column(nullable = false, length = 1000)
	private String description;

	@NotNull(message = "Date is required")
	@JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm")
	@Column(nullable = false)
	private LocalDateTime date;

	@NotBlank(message = "Location is required")
	@Size(max = 255, message = "Location must not exceed 255 characters")
	@Column(nullable = false)
	private String location;

	@Size(max = 255, message = "Club name must not exceed 255 characters")
	private String club;

	@Size(max = 500, message = "Image URL must not exceed 500 characters")
	private String image;

	@Column(columnDefinition = "TEXT")
	private String schedule;

	@Column(columnDefinition = "TEXT")
	private String expectations;
}
