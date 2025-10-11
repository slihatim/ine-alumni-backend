package com.ine.backend.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ine.backend.entities.Company;

@Repository
public interface CompanyRepository extends JpaRepository<Company, Long> {

	Optional<Company> findByNameIgnoreCase(String name);

	@Query("SELECT DISTINCT c FROM Company c WHERE EXISTS "
			+ "(SELECT l FROM Laureat l WHERE l.currentCompany = c AND l.isAccountVerified = true)")
	Page<Company> findCompaniesWithVerifiedLaureats(Pageable pageable);

	@Query("SELECT DISTINCT c FROM Company c WHERE "
			+ "LOWER(c.name) LIKE LOWER(CONCAT('%', :searchTerm, '%')) AND EXISTS "
			+ "(SELECT l FROM Laureat l WHERE l.currentCompany = c AND l.isAccountVerified = true)")
	Page<Company> searchCompaniesWithVerifiedLaureats(@Param("searchTerm") String searchTerm, Pageable pageable);

	@Query("SELECT c FROM Company c WHERE LOWER(c.name) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR "
			+ "LOWER(c.industry) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR "
			+ "LOWER(c.location) LIKE LOWER(CONCAT('%', :searchTerm, '%'))")
	Page<Company> searchCompanies(@Param("searchTerm") String searchTerm, Pageable pageable);

	@Query("SELECT c, COUNT(l) as alumniCount FROM Company c "
			+ "LEFT JOIN Laureat l ON l.currentCompany.id = c.id AND l.isAccountVerified = true " + "GROUP BY c "
			+ "ORDER BY COUNT(l) DESC")
	Page<Object[]> findCompaniesWithAlumniCount(Pageable pageable);

	@Query("SELECT COUNT(l) FROM Laureat l WHERE l.currentCompany.id = :companyId AND l.isAccountVerified = true")
	Long countVerifiedLaureatsByCompanyId(@Param("companyId") Long companyId);

	@Query("SELECT c FROM Company c WHERE EXISTS (SELECT 1 FROM Laureat l WHERE l.currentCompany.id = c.id AND l.isAccountVerified = true)")
	List<Company> findCompaniesWithAlumni();

	@Query("SELECT c.industry, COUNT(c) FROM Company c WHERE c.industry IS NOT NULL GROUP BY c.industry ORDER BY COUNT(c) DESC")
	List<Object[]> countByIndustry();

	@Query("SELECT c.location, COUNT(c) FROM Company c WHERE c.location IS NOT NULL GROUP BY c.location ORDER BY COUNT(c) DESC")
	List<Object[]> countByLocation();
}
