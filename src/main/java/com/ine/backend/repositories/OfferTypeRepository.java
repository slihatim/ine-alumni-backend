package com.ine.backend.repositories;

import com.ine.backend.entities.OfferType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OfferTypeRepository extends JpaRepository<OfferType, Long> {
    Optional<OfferType> findByNameIgnoreCaseAndCustomTypeIsNull(String name);
    Optional<OfferType> findByNameIgnoreCaseAndCustomTypeIgnoreCase(String name, String customType);
}


