package com.ine.backend.repositories;

import com.ine.backend.entities.Offer;
import com.ine.backend.entities.OfferType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OfferRepository extends JpaRepository<Offer, Long> {
    // FIXED: Import from entities package, removed duplicate content
    List<Offer> findByType(OfferType type);
    
    // Method to find offers by type and custom type for OTHER category
    List<Offer> findByTypeAndCustomTypeIgnoreCase(OfferType type, String customType);
}