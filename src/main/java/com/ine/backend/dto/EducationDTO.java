package com.ine.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class EducationDTO {
	private Long id;
	private String institutionName;
	private String degree;
	private String fieldOfStudy;
	private Integer startYear;
	private Integer endYear;
	private String description;
	private boolean current;
}
