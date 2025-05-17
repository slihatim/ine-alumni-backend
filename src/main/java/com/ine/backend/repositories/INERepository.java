package com.ine.backend.repositories;

import com.ine.backend.entities.INE;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface INERepository extends JpaRepository<INE, Long> {
}
