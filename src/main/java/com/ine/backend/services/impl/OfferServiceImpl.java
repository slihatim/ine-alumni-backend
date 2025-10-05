package com.ine.backend.services.impl;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ine.backend.dto.OfferRequestDto;
import com.ine.backend.dto.OfferResponseDto;
import com.ine.backend.entities.InptUser;
import com.ine.backend.entities.Offer;
import com.ine.backend.entities.OfferApplication;
import com.ine.backend.entities.OfferType;
import com.ine.backend.exceptions.OfferNotFoundException;
import com.ine.backend.repositories.OfferApplicationRepository;
import com.ine.backend.repositories.OfferRepository;
import com.ine.backend.services.OfferService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OfferServiceImpl implements OfferService {

	private final OfferRepository offerRepository;
	private final OfferApplicationRepository offerApplicationRepository;
	// REMOVED: OfferTypeRepository no longer needed since we're using enum

	@Override
	@Transactional(readOnly = true)
	public List<OfferResponseDto> getAllOffers() {
		return offerRepository.findAll().stream().map(this::mapToResponse).collect(Collectors.toList());
	}

	@Override
	@Transactional(readOnly = true)
	public OfferResponseDto getOfferById(Long id) {
		Offer offer = offerRepository.findById(id).orElseThrow(() -> new OfferNotFoundException(id));
		return mapToResponse(offer);
	}

	@Override
	@Transactional
	public OfferResponseDto createOffer(OfferRequestDto requestDto) {
		Offer offer = Offer.builder().title(requestDto.getTitle()).company(requestDto.getCompany())
				.location(requestDto.getLocation()).type(requestDto.getType()).customType(requestDto.getCustomType())
				.description(requestDto.getDescription()).duration(requestDto.getDuration()).link(requestDto.getLink())
				.build();

		Offer saved = offerRepository.save(offer);
		return mapToResponse(saved);
	}

	@Override
	@Transactional
	public OfferResponseDto updateOffer(Long id, OfferRequestDto requestDto) {
		Offer offer = offerRepository.findById(id).orElseThrow(() -> new OfferNotFoundException(id));

		// Update fields with proper enum conversion
		offer.setTitle(requestDto.getTitle());
		offer.setCompany(requestDto.getCompany());
		offer.setLocation(requestDto.getLocation());
		offer.setType(requestDto.getType());
		offer.setCustomType(requestDto.getCustomType());
		offer.setDescription(requestDto.getDescription());
		offer.setDuration(requestDto.getDuration());
		offer.setLink(requestDto.getLink());

		Offer updated = offerRepository.save(offer);
		return mapToResponse(updated);
	}

	@Override
	@Transactional
	public void deleteOffer(Long id) {
		if (!offerRepository.existsById(id)) {
			throw new OfferNotFoundException(id);
		}
		offerRepository.deleteById(id);
	}

	@Override
	@Transactional
	public void applyToOffer(Long offerId, InptUser applicant, String message, String resumeUrl) {
		Offer offer = offerRepository.findById(offerId).orElseThrow(() -> new OfferNotFoundException(offerId));

		if (applicant == null || applicant.getId() == null) {
			throw new IllegalArgumentException("Applicant must be provided");
		}

		boolean alreadyApplied = offerApplicationRepository.existsByOfferIdAndApplicantId(offerId, applicant.getId());
		if (alreadyApplied) {
			return; // FIXED: Keep idempotent behavior as in original code, don't throw exception
		}

		OfferApplication application = OfferApplication.builder().offer(offer).applicant(applicant).message(message)
				.resumeUrl(resumeUrl).build();

		offerApplicationRepository.save(application);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Long> getOfferAppliers(Long offerId) {
		// Ensure offer exists
		offerRepository.findById(offerId).orElseThrow(() -> new OfferNotFoundException(offerId));
		return offerApplicationRepository.findByOfferId(offerId).stream()
				.map(app -> app.getApplicant() != null ? app.getApplicant().getId() : null).filter(Objects::nonNull)
				.collect(Collectors.toList());
	}

	// ADDED: Method to get offers by type as requested in PR comments
	@Override
	@Transactional(readOnly = true)
	public List<OfferResponseDto> getOffersByType(OfferType type) {
		return offerRepository.findByType(type).stream().map(this::mapToResponse).collect(Collectors.toList());
	}

	@Override
	@Transactional(readOnly = true)
	public List<OfferResponseDto> getOffersByCompany(String company) {
		return offerRepository.findByCompanyIgnoreCase(company).stream().map(this::mapToResponse)
				.collect(Collectors.toList());
	}

	@Override
	@Transactional(readOnly = true)
	public List<OfferResponseDto> getOffersByLocation(String location) {
		return offerRepository.findByLocationIgnoreCase(location).stream().map(this::mapToResponse)
				.collect(Collectors.toList());
	}

	// SIMPLIFIED: Removed normalizeType and resolveOfferType methods as they're no
	// longer needed with enum
	private OfferResponseDto mapToResponse(Offer offer) {
		return OfferResponseDto.builder().id(offer.getId()).title(offer.getTitle()).company(offer.getCompany())
				.location(offer.getLocation()).type(offer.getType()).customType(offer.getCustomType())
				.duration(offer.getDuration()).description(offer.getDescription()).link(offer.getLink())
				.postedAt(offer.getCreatedAt()).build();
	}
}
