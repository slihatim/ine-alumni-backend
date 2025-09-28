package com.ine.backend.dto;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EventRequestDto {

	@NotBlank(message = "Title is required")
	@Size(max = 255, message = "Title must not exceed 255 characters")
	private String title;

	@NotBlank(message = "Description is required")
	@Size(max = 1000, message = "Description must not exceed 1000 characters")
	private String description;

	@NotNull(message = "Date is required")
	@JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm")
	private LocalDateTime date;

	@NotBlank(message = "Location is required")
	@Size(max = 255, message = "Location must not exceed 255 characters")
	private String location;

	@Size(max = 255, message = "Club name must not exceed 255 characters")
	private String club;

	@Size(max = 500, message = "Image URL must not exceed 500 characters")
	private String image;

	@Size(max = 5000, message = "Schedule must not exceed 5000 characters")
	private String schedule;

	@Size(max = 5000, message = "Expectations must not exceed 5000 characters")
	private String expectations;
}
