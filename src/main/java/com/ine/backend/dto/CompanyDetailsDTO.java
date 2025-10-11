package com.ine.backend.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class CompanyDetailsDTO {
	private Long id;
	private String name;
	private String description;
	private String industry;
	private String website;
	private String location;
	private String logo;
	private String hrContactName;
	private String hrContactEmail;
	private String hrContactPhone;
	private Long alumniCount;
	private Integer totalReviews;

	private List<LaureatDTO> alumni;
	private List<CompanyReviewDTO> reviews;
	private List<ExternalLinkDTO> externalLinks;
}
