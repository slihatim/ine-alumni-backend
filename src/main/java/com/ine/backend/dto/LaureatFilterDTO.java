package com.ine.backend.dto;

import java.util.List;

import com.ine.backend.entities.Domain;
import com.ine.backend.entities.Major;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * DTO for filtering laureates with various criteria
 */
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class LaureatFilterDTO {
	private String searchTerm; // General search term
	private List<Major> majors; // Filter by majors
	private List<Long> companyIds; // Filter by companies
	private List<String> positions; // Filter by current positions
	private List<Integer> graduationYears; // Filter by graduation years
	private List<String> cities; // Filter by cities
	private List<String> countries; // Filter by countries
	private List<Domain> domains; // Filter by work domains
	private List<String> skills; // Filter by skills
}
