package com.ine.backend.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class CompanyReviewDTO {
	private Long id;
	private Integer rating;
	private String comment;
	private LocalDateTime createdAt;

	private Long alumniId;
	private String alumniName;
	private String alumniProfilePicture;
	private String alumniCurrentPosition;
	private Integer alumniGraduationYear;
}
