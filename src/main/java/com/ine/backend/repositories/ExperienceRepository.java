package com.ine.backend.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ine.backend.entities.Domain;
import com.ine.backend.entities.Experience;
import com.ine.backend.entities.Laureat;

@Repository
public interface ExperienceRepository extends JpaRepository<Experience, Long> {
	List<Experience> findByLaureatOrderByStartYearDesc(Laureat laureat);
	List<Experience> findByLaureatIdOrderByStartYearDesc(Long laureatId);
	List<Experience> findByCompanyIdOrderByStartYearDesc(Long companyId);

	@Query("SELECT DISTINCT e.domain FROM Experience e WHERE e.laureat.id = :laureatId AND e.domain IS NOT NULL")
	List<Domain> findDistinctDomainsByLaureatId(@Param("laureatId") Long laureatId);
}
