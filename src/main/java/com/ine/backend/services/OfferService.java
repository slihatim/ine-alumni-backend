package com.ine.backend.services;

import com.ine.backend.dto.OfferRequestDto;
import com.ine.backend.dto.OfferResponseDto;
import com.ine.backend.entities.InptUser;

import java.util.List;

public interface OfferService {
    List<OfferResponseDto> getAllOffers();
    OfferResponseDto createOffer(OfferRequestDto requestDto);
    void applyToOffer(Long offerId, InptUser applicant, String message, String resumeUrl);
}


