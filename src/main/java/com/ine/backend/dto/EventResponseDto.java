package com.ine.backend.dto;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EventResponseDto {

	private Long id;

	private String title;

	private String description;

	@JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm")
	private LocalDateTime date;

	private String location;

	private String club;

	private String image;

	private String schedule;

	private String expectations;
}
