package com.ine.backend.services;

import com.ine.backend.dto.OfferRequestDto;
import com.ine.backend.dto.OfferResponseDto;
import com.ine.backend.entities.InptUser;
import com.ine.backend.entities.OfferType;

import java.util.List;

public interface OfferService {
    List<OfferResponseDto> getAllOffers();
    OfferResponseDto getOfferById(Long id);
    OfferResponseDto createOffer(OfferRequestDto requestDto);
    OfferResponseDto updateOffer(Long id, OfferRequestDto requestDto);
    void deleteOffer(Long id);
    void applyToOffer(Long offerId, InptUser applicant, String message, String resumeUrl);
    
    // ADDED: Method to retrieve offers by type as suggested in PR comments
    List<OfferResponseDto> getOffersByType(OfferType type);
}