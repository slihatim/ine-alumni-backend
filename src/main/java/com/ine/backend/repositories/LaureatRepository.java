package com.ine.backend.repositories;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ine.backend.entities.Company;
import com.ine.backend.entities.Domain;
import com.ine.backend.entities.Laureat;
import com.ine.backend.entities.Major;

@Repository
public interface LaureatRepository extends JpaRepository<Laureat, Long> {

	Page<Laureat> findByIsAccountVerifiedTrue(Pageable pageable);

	@Query("SELECT l FROM Laureat l WHERE l.isAccountVerified = true AND "
			+ "LOWER(l.fullName) LIKE LOWER(CONCAT('%', :searchTerm, '%'))")
	Page<Laureat> findByFullNameContainingIgnoreCaseAndVerified(@Param("searchTerm") String searchTerm,
			Pageable pageable);

	@Query("SELECT l FROM Laureat l WHERE l.isAccountVerified = true AND " + "l.currentCompany.id = :companyId")
	Page<Laureat> findByCurrentCompanyIdAndVerified(@Param("companyId") Long companyId, Pageable pageable);

	@Query("SELECT l FROM Laureat l WHERE l.isAccountVerified = true AND ("
			+ "LOWER(l.fullName) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR "
			+ "LOWER(l.city) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR "
			+ "LOWER(l.country) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR "
			+ "LOWER(l.major) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR "
			+ "LOWER(l.currentPosition) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR "
			+ "LOWER(l.currentCompany.name) LIKE LOWER(CONCAT('%', :searchTerm, '%'))" + ")")
	Page<Laureat> searchLaureats(@Param("searchTerm") String searchTerm, Pageable pageable);

	Page<Laureat> findByCurrentCompanyAndIsAccountVerifiedTrue(Company company, Pageable pageable);

	// Advanced filtering queries
	@Query("SELECT DISTINCT l FROM Laureat l " + "LEFT JOIN l.experiences e " + "LEFT JOIN l.skills s "
			+ "WHERE l.isAccountVerified = true " + "AND (:majors IS NULL OR l.major IN :majors) "
			+ "AND (:companyIds IS NULL OR l.currentCompany.id IN :companyIds) "
			+ "AND (:graduationYears IS NULL OR l.graduationYear IN :graduationYears) "
			+ "AND (:cities IS NULL OR l.city IN :cities) " + "AND (:countries IS NULL OR l.country IN :countries) "
			+ "AND (:positions IS NULL OR LOWER(l.currentPosition) IN :positions) "
			+ "AND (:domains IS NULL OR e.domain IN :domains) " + "AND (:skills IS NULL OR LOWER(s.name) IN :skills)")
	Page<Laureat> findWithFilters(@Param("majors") List<Major> majors, @Param("companyIds") List<Long> companyIds,
			@Param("graduationYears") List<Integer> graduationYears, @Param("cities") List<String> cities,
			@Param("countries") List<String> countries, @Param("positions") List<String> positions,
			@Param("domains") List<Domain> domains, @Param("skills") List<String> skills, Pageable pageable);

	// Statistics queries
	@Query("SELECT COUNT(l) FROM Laureat l WHERE l.isAccountVerified = true")
	Long countVerifiedLaureats();

	@Query("SELECT l.graduationYear, COUNT(l) FROM Laureat l WHERE l.isAccountVerified = true GROUP BY l.graduationYear ORDER BY l.graduationYear DESC")
	List<Object[]> countByGraduationYear();

	@Query("SELECT l.major, COUNT(l) FROM Laureat l WHERE l.isAccountVerified = true GROUP BY l.major")
	List<Object[]> countByMajor();

	@Query("SELECT l.city, l.country, COUNT(l) FROM Laureat l WHERE l.isAccountVerified = true AND l.city IS NOT NULL AND l.country IS NOT NULL GROUP BY l.city, l.country ORDER BY COUNT(l) DESC")
	List<Object[]> countByLocation();
}
