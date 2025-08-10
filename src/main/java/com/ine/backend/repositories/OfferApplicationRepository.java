package com.ine.backend.repositories;

import com.ine.backend.entities.OfferApplication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OfferApplicationRepository extends JpaRepository<OfferApplication, Long> {
    boolean existsByOfferIdAndApplicantId(Long offerId, Long applicantId);
}


