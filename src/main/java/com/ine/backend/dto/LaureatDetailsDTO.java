package com.ine.backend.dto;

import java.time.LocalDate;
import java.util.List;

import com.ine.backend.entities.Gender;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class LaureatDetailsDTO extends LaureatDTO {
	private String bio;
	private String email;
	private String phoneNumber;
	private LocalDate birthDate;
	private Gender gender;
	private String linkedinId;

	private List<EducationDTO> educations;
	private List<ExperienceDTO> experiences;
	private List<SkillDTO> detailedSkills;
	private List<ExternalLinkDTO> externalLinks;
}
