package com.ine.backend.repositories;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ine.backend.entities.CompanyReview;

@Repository
public interface CompanyReviewRepository extends JpaRepository<CompanyReview, Long> {
	Page<CompanyReview> findByCompanyIdOrderByCreatedAtDesc(Long companyId, Pageable pageable);

	@Query("SELECT AVG(cr.rating) FROM CompanyReview cr WHERE cr.company.id = :companyId")
	Double findAverageRatingByCompanyId(@Param("companyId") Long companyId);

	Long countByCompanyId(Long companyId);

	Optional<CompanyReview> findByCompanyIdAndLaureatId(Long companyId, Long laureatId);

	boolean existsByCompanyIdAndLaureatId(Long companyId, Long laureatId);
}
