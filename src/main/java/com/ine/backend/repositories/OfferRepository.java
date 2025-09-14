package com.ine.backend.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ine.backend.entities.Offer;
import com.ine.backend.entities.OfferType;

@Repository
public interface OfferRepository extends JpaRepository<Offer, Long> {
	// FIXED: Import from entities package, removed duplicate content
	List<Offer> findByType(OfferType type);

	// New finder methods for filters
	List<Offer> findByCompanyIgnoreCase(String company);

	List<Offer> findByLocationIgnoreCase(String location);
}
