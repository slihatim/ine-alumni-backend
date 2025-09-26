package com.ine.backend.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ine.backend.entities.OfferApplication;

@Repository
public interface OfferApplicationRepository extends JpaRepository<OfferApplication, Long> {
	boolean existsByOfferIdAndApplicantId(Long offerId, Long applicantId);

	List<OfferApplication> findByOfferId(Long offerId);
}
