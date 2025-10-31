package com.ine.backend.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ine.backend.dto.*;
import com.ine.backend.entities.*;
import com.ine.backend.exceptions.ResourceNotFoundException;
import com.ine.backend.repositories.*;

@Service
@Transactional(readOnly = true)
public class LaureatService {

	@Autowired
	private LaureatRepository laureatRepository;

	@Autowired
	private EducationRepository educationRepository;

	@Autowired
	private ExperienceRepository experienceRepository;

	@Autowired
	private SkillRepository skillRepository;

	@Autowired
	private ExternalLinkRepository externalLinkRepository;

	public PageResponseDTO<LaureatDTO> getAllLaureats(int page, int size, String sortBy, String sortDir) {
		Sort sort = sortDir.equalsIgnoreCase("desc") ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();

		Pageable pageable = PageRequest.of(page, size, sort);
		Page<Laureat> laureatPage = laureatRepository.findByIsAccountVerifiedTrue(pageable);

		Page<LaureatDTO> dtoPage = laureatPage.map(this::convertToDTO);
		return PageResponseDTO.from(dtoPage);
	}

	public PageResponseDTO<LaureatDTO> searchLaureats(String searchTerm, int page, int size) {
		Pageable pageable = PageRequest.of(page, size, Sort.by("fullName"));
		Page<Laureat> laureatPage = laureatRepository.searchLaureats(searchTerm, pageable);

		Page<LaureatDTO> dtoPage = laureatPage.map(this::convertToDTO);
		return PageResponseDTO.from(dtoPage);
	}

	public PageResponseDTO<LaureatDTO> filterLaureats(LaureatFilterDTO filter, int page, int size, String sortBy,
			String sortDir) {
		Sort sort = sortDir.equalsIgnoreCase("desc") ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
		Pageable pageable = PageRequest.of(page, size, sort);

		// Convert string lists to lowercase for case-insensitive matching
		List<String> positions = filter.getPositions() != null
				? filter.getPositions().stream().map(String::toLowerCase).collect(Collectors.toList())
				: null;
		List<String> skills = filter.getSkills() != null
				? filter.getSkills().stream().map(String::toLowerCase).collect(Collectors.toList())
				: null;

		Page<Laureat> laureatPage = laureatRepository.findWithFilters(filter.getMajors(), filter.getCompanyIds(),
				filter.getGraduationYears(), filter.getCities(), filter.getCountries(), positions, filter.getDomains(),
				skills, pageable);

		Page<LaureatDTO> dtoPage = laureatPage.map(this::convertToDTO);
		return PageResponseDTO.from(dtoPage);
	}

	public LaureatDetailsDTO getLaureatDetails(Long id) {
		Laureat laureat = laureatRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Lauréat not found with id: " + id));

		if (!laureat.getIsAccountVerified()) {
			throw new ResourceNotFoundException("Lauréat not found with id: " + id);
		}

		return convertToDetailsDTO(laureat);
	}

	public List<Major> getAvailableMajors() {
		return laureatRepository.countByMajor().stream().map(result -> (Major) result[0]).collect(Collectors.toList());
	}

	public List<Integer> getAvailableGraduationYears() {
		return laureatRepository.countByGraduationYear().stream().map(result -> (Integer) result[0])
				.collect(Collectors.toList());
	}

	private LaureatDTO convertToDTO(Laureat laureat) {
		LaureatDTO dto = new LaureatDTO();
		dto.setId(laureat.getId());
		dto.setFullName(laureat.getFullName());
		dto.setMajor(laureat.getMajor());
		dto.setGraduationYear(laureat.getGraduationYear());
		dto.setCity(laureat.getCity());
		dto.setCountry(laureat.getCountry());
		dto.setCurrentPosition(laureat.getCurrentPosition());
		dto.setProfilePicture(laureat.getProfilePicture());

		if (laureat.getCurrentCompany() != null) {
			dto.setCurrentCompany(
					new CompanyDTO(laureat.getCurrentCompany().getId(), laureat.getCurrentCompany().getName()));
		}

		// Set domains from experiences
		List<Domain> domains = experienceRepository.findDistinctDomainsByLaureatId(laureat.getId());
		dto.setDomains(domains);

		// Set top skills (limit to 5 for listing view)
		List<String> skills = skillRepository.findSkillNamesByLaureatId(laureat.getId()).stream().limit(5)
				.collect(Collectors.toList());
		dto.setSkills(skills);

		return dto;
	}

	private LaureatDetailsDTO convertToDetailsDTO(Laureat laureat) {
		LaureatDetailsDTO dto = new LaureatDetailsDTO();

		// Copy basic fields
		dto.setId(laureat.getId());
		dto.setFullName(laureat.getFullName());
		dto.setMajor(laureat.getMajor());
		dto.setGraduationYear(laureat.getGraduationYear());
		dto.setCity(laureat.getCity());
		dto.setCountry(laureat.getCountry());
		dto.setCurrentPosition(laureat.getCurrentPosition());
		dto.setProfilePicture(laureat.getProfilePicture());
		dto.setBio(laureat.getBio());

		// include all information (no privacy controls)
		dto.setEmail(laureat.getEmail());
		dto.setPhoneNumber(laureat.getPhoneNumber());
		dto.setBirthDate(laureat.getBirthDate());
		dto.setGender(laureat.getGender());
		dto.setLinkedinId(laureat.getLinkedinId());

		if (laureat.getCurrentCompany() != null) {
			dto.setCurrentCompany(
					new CompanyDTO(laureat.getCurrentCompany().getId(), laureat.getCurrentCompany().getName()));
		}

		// Set complete profile data
		List<Education> educations = educationRepository.findByLaureatIdOrderByStartYearDesc(laureat.getId());
		dto.setEducations(educations.stream().map(this::convertEducationToDTO).collect(Collectors.toList()));

		List<Experience> experiences = experienceRepository.findByLaureatIdOrderByStartYearDesc(laureat.getId());
		dto.setExperiences(experiences.stream().map(this::convertExperienceToDTO).collect(Collectors.toList()));

		List<Skill> skills = skillRepository.findByLaureatId(laureat.getId());
		dto.setDetailedSkills(skills.stream().map(this::convertSkillToDTO).collect(Collectors.toList()));

		List<ExternalLink> links = externalLinkRepository.findByLaureatId(laureat.getId());
		dto.setExternalLinks(links.stream().map(this::convertLinkToDTO).collect(Collectors.toList()));

		return dto;
	}

	private EducationDTO convertEducationToDTO(Education education) {
		EducationDTO dto = new EducationDTO();
		dto.setId(education.getId());
		dto.setInstitutionName(education.getInstitutionName());
		dto.setDegree(education.getDegree());
		dto.setFieldOfStudy(education.getFieldOfStudy());
		dto.setStartYear(education.getStartYear());
		dto.setEndYear(education.getEndYear());
		dto.setDescription(education.getDescription());
		dto.setCurrent(education.isCurrent());
		return dto;
	}

	private ExperienceDTO convertExperienceToDTO(Experience experience) {
		ExperienceDTO dto = new ExperienceDTO();
		dto.setId(experience.getId());
		dto.setJobTitle(experience.getJobTitle());
		dto.setLocation(experience.getLocation());
		dto.setDomain(experience.getDomain());
		dto.setStartYear(experience.getStartYear());
		dto.setEndYear(experience.getEndYear());
		dto.setDescription(experience.getDescription());
		dto.setCurrent(experience.isCurrent());

		if (experience.getCompany() != null) {
			dto.setCompany(new CompanyDTO(experience.getCompany().getId(), experience.getCompany().getName()));
		}

		return dto;
	}

	private SkillDTO convertSkillToDTO(Skill skill) {
		SkillDTO dto = new SkillDTO();
		dto.setId(skill.getId());
		dto.setName(skill.getName());
		dto.setCategory(skill.getCategory());
		return dto;
	}

	private ExternalLinkDTO convertLinkToDTO(ExternalLink link) {
		ExternalLinkDTO dto = new ExternalLinkDTO();
		dto.setId(link.getId());
		dto.setTitle(link.getTitle());
		dto.setUrl(link.getUrl());
		dto.setLinkType(link.getLinkType());
		return dto;
	}
}
