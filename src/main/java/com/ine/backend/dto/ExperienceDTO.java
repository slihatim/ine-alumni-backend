package com.ine.backend.dto;

import com.ine.backend.entities.Domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class ExperienceDTO {
	private Long id;
	private String jobTitle;
	private CompanyDTO company;
	private String location;
	private Domain domain;
	private Integer startYear;
	private Integer endYear;
	private String description;
	private boolean current;
}
