package com.ine.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class CompanyDTO {
	private Long id;
	private String name;
	private String industry;
	private String location;
	private String logo;
	private Long alumniCount;
	private Integer totalReviews;

	public CompanyDTO(Long id, String name) {
		this.id = id;
		this.name = name;
	}

	public CompanyDTO(Long id, String name, Long alumniCount) {
		this.id = id;
		this.name = name;
		this.alumniCount = alumniCount;
	}
}
