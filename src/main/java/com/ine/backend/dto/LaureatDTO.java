package com.ine.backend.dto;

import java.util.List;

import com.ine.backend.entities.Domain;
import com.ine.backend.entities.Major;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class LaureatDTO {
	private Long id;
	private String fullName;
	private Major major;
	private Integer graduationYear;
	private String city;
	private String country;
	private String currentPosition;
	private String profilePicture;
	private CompanyDTO currentCompany;
	private List<Domain> domains; // Derived from experiences
	private List<String> skills;

	// Constructor for easy mapping
	public LaureatDTO(Long id, String fullName, Major major, Integer graduationYear, String city, String country,
			Long companyId, String companyName) {
		this.id = id;
		this.fullName = fullName;
		this.major = major;
		this.graduationYear = graduationYear;
		this.city = city;
		this.country = country;
		this.currentCompany = companyId != null ? new CompanyDTO(companyId, companyName) : null;
	}
}
