package com.ine.backend.services;

import com.ine.backend.dto.OfferRequestDto;
import com.ine.backend.dto.OfferResponseDto;
import com.ine.backend.entities.InptUser;
import com.ine.backend.entities.Offer;
import com.ine.backend.entities.OfferApplication;
import com.ine.backend.entities.OfferType;
import com.ine.backend.exceptions.OfferNotFoundException;
import com.ine.backend.repositories.OfferApplicationRepository;
import com.ine.backend.repositories.OfferRepository;
import com.ine.backend.repositories.OfferTypeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OfferServiceImpl implements OfferService {

    private final OfferRepository offerRepository;
    private final OfferTypeRepository offerTypeRepository;
    private final OfferApplicationRepository offerApplicationRepository;

    @Override
    @Transactional(readOnly = true)
    public List<OfferResponseDto> getAllOffers() {
        return offerRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public OfferResponseDto createOffer(OfferRequestDto requestDto) {
        OfferType offerType = resolveOfferType(requestDto.getType(), requestDto.getCustomType());

        Offer offer = Offer.builder()
                .title(requestDto.getTitle())
                .company(requestDto.getCompany())
                .location(requestDto.getLocation())
                .type(offerType)
                .description(requestDto.getDescription())
                .duration(requestDto.getDuration())
                .link(requestDto.getLink())
                .build();

        Offer saved = offerRepository.save(offer);
        return mapToResponse(saved);
    }

    @Override
    @Transactional
    public void applyToOffer(Long offerId, InptUser applicant, String message, String resumeUrl) {
        Offer offer = offerRepository.findById(offerId)
                .orElseThrow(() -> new OfferNotFoundException(offerId));

        if (applicant == null || applicant.getId() == null) {
            throw new IllegalArgumentException("Applicant must be provided");
        }

        boolean alreadyApplied = offerApplicationRepository.existsByOfferIdAndApplicantId(offerId, applicant.getId());
        if (alreadyApplied) {
            return; // idempotent
        }

        OfferApplication application = OfferApplication.builder()
                .offer(offer)
                .applicant(applicant)
                .message(message)
                .resumeUrl(resumeUrl)
                .build();

        offerApplicationRepository.save(application);
    }

    private OfferType resolveOfferType(String type, String customType) {
        String normalized = normalizeType(type);

        if ("other".equalsIgnoreCase(normalized)) {
            String custom = customType == null ? null : customType.trim();
            return offerTypeRepository.findByNameIgnoreCaseAndCustomTypeIgnoreCase(normalized, custom)
                    .orElseGet(() -> offerTypeRepository.save(OfferType.builder()
                            .name(normalized)
                            .customType(custom)
                            .build()));
        }

        return offerTypeRepository.findByNameIgnoreCaseAndCustomTypeIsNull(normalized)
                .orElseGet(() -> offerTypeRepository.save(OfferType.builder()
                        .name(normalized)
                        .customType(null)
                        .build()));
    }

    private String normalizeType(String type) {
        if (type == null) return "other";
        String t = type.trim().toLowerCase(Locale.ROOT);
        switch (t) {
            case "stage":
                return "internship";
            case "emploi":
                return "job";
            case "alternance":
                return "alternance";
            case "benevolat":
            case "bénévolat":
                return "benevolat";
            case "autre":
            case "other":
                return "other";
            case "job":
            case "internship":
                return t;
            default:
                return "other";
        }
    }

    private OfferResponseDto mapToResponse(Offer offer) {
        String name = offer.getType() != null ? offer.getType().getName() : null;
        String custom = offer.getType() != null ? offer.getType().getCustomType() : null;
        return OfferResponseDto.builder()
                .id(offer.getId())
                .title(offer.getTitle())
                .company(offer.getCompany())
                .location(offer.getLocation())
                .type(name)
                .customType(custom)
                .duration(offer.getDuration())
                .description(offer.getDescription())
                .link(offer.getLink())
                .postedAt(offer.getCreatedAt())
                .build();
    }
}


