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
public class CompanyService {

	@Autowired
	private CompanyRepository companyRepository;

	@Autowired
	private CompanyReviewRepository reviewRepository;

	@Autowired
	private LaureatRepository laureatRepository;

	@Autowired
	private ExternalLinkRepository externalLinkRepository;

	public PageResponseDTO<CompanyDTO> getAllCompaniesWithAlumni(int page, int size, String sortBy, String sortDir) {
		Sort sort = sortDir.equalsIgnoreCase("desc") ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();

		Pageable pageable = PageRequest.of(page, size, sort);

		if ("alumniCount".equals(sortBy)) {
			// Use custom query for sorting by alumni count
			Page<Object[]> companyPage = companyRepository.findCompaniesWithAlumniCount(pageable);
			return convertToCompanyDTOPage(companyPage);
		} else {
			// Use regular query
			Page<Company> companyPage = companyRepository.findCompaniesWithVerifiedLaureats(pageable);
			return PageResponseDTO.from(companyPage.map(this::convertToDTO));
		}
	}

	public PageResponseDTO<CompanyDTO> searchCompanies(String searchTerm, int page, int size) {
		Pageable pageable = PageRequest.of(page, size, Sort.by("name"));
		Page<Company> companyPage = companyRepository.searchCompaniesWithVerifiedLaureats(searchTerm, pageable);
		return PageResponseDTO.from(companyPage.map(this::convertToDTO));
	}

	public CompanyDetailsDTO getCompanyDetails(Long id) {
		Company company = companyRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Company not found with id: " + id));

		return convertToDetailsDTO(company);
	}

	public PageResponseDTO<LaureatDTO> getCompanyAlumni(Long companyId, int page, int size, String sortBy,
			String sortDir) {
		// Verify company exists
		companyRepository.findById(companyId)
				.orElseThrow(() -> new ResourceNotFoundException("Company not found with id: " + companyId));

		Sort sort = sortDir.equalsIgnoreCase("desc") ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();

		Pageable pageable = PageRequest.of(page, size, sort);
		Page<Laureat> laureatPage = laureatRepository.findByCurrentCompanyIdAndVerified(companyId, pageable);

		return PageResponseDTO.from(laureatPage.map(this::convertLaureatToDTO));
	}

	public PageResponseDTO<CompanyReviewDTO> getCompanyReviews(Long companyId, int page, int size) {
		Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());
		Page<CompanyReview> reviewPage = reviewRepository.findByCompanyIdOrderByCreatedAtDesc(companyId, pageable);

		return PageResponseDTO.from(reviewPage.map(this::convertReviewToDTO));
	}

	private CompanyDTO convertToDTO(Company company) {
		CompanyDTO dto = new CompanyDTO();
		dto.setId(company.getId());
		dto.setName(company.getName());
		dto.setIndustry(company.getIndustry());
		dto.setLocation(company.getLocation());
		dto.setLogo(company.getLogo());

		// Set alumni count
		Long alumniCount = companyRepository.countVerifiedLaureatsByCompanyId(company.getId());
		dto.setAlumniCount(alumniCount);

		// Set review stats
		dto.setTotalReviews(reviewRepository.countByCompanyId(company.getId()).intValue());

		return dto;
	}

	private CompanyDetailsDTO convertToDetailsDTO(Company company) {
		CompanyDetailsDTO dto = new CompanyDetailsDTO();
		dto.setId(company.getId());
		dto.setName(company.getName());
		dto.setDescription(company.getDescription());
		dto.setIndustry(company.getIndustry());
		dto.setWebsite(company.getWebsite());
		dto.setLocation(company.getLocation());
		dto.setLogo(company.getLogo());
		dto.setHrContactName(company.getHrContactName());
		dto.setHrContactEmail(company.getHrContactEmail());
		dto.setHrContactPhone(company.getHrContactPhone());

		// Set alumni count and review stats
		Long alumniCount = companyRepository.countVerifiedLaureatsByCompanyId(company.getId());
		dto.setAlumniCount(alumniCount);

		dto.setTotalReviews(reviewRepository.countByCompanyId(company.getId()).intValue());

		// Get external links
		List<ExternalLink> links = externalLinkRepository.findByCompanyId(company.getId());
		dto.setExternalLinks(links.stream().map(this::convertLinkToDTO).collect(Collectors.toList()));

		return dto;
	}

	private CompanyReviewDTO convertReviewToDTO(CompanyReview review) {
		CompanyReviewDTO dto = new CompanyReviewDTO();
		dto.setId(review.getId());
		dto.setRating(review.getRating());
		dto.setComment(review.getComment());
		dto.setCreatedAt(review.getCreatedAt());

		// Always include alumni information for profile access
		if (review.getLaureat() != null) {
			dto.setAlumniId(review.getLaureat().getId());
			dto.setAlumniName(review.getLaureat().getFullName());
			dto.setAlumniProfilePicture(review.getLaureat().getProfilePicture());
			dto.setAlumniCurrentPosition(review.getLaureat().getCurrentPosition());
			dto.setAlumniGraduationYear(review.getLaureat().getGraduationYear());
		}

		return dto;
	}

	private LaureatDTO convertLaureatToDTO(Laureat laureat) {
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

	private PageResponseDTO<CompanyDTO> convertToCompanyDTOPage(Page<Object[]> companyPage) {
		List<CompanyDTO> companies = companyPage.getContent().stream().map(result -> {
			Company company = (Company) result[0];
			Long alumniCount = (Long) result[1];

			CompanyDTO dto = convertToDTO(company);
			dto.setAlumniCount(alumniCount);
			return dto;
		}).collect(Collectors.toList());

		PageResponseDTO<CompanyDTO> response = new PageResponseDTO<>();
		response.setContent(companies);
		response.setPageNumber(companyPage.getNumber());
		response.setPageSize(companyPage.getSize());
		response.setTotalElements(companyPage.getTotalElements());
		response.setTotalPages(companyPage.getTotalPages());
		response.setFirst(companyPage.isFirst());
		response.setLast(companyPage.isLast());
		response.setEmpty(companyPage.isEmpty());

		return response;
	}
}
