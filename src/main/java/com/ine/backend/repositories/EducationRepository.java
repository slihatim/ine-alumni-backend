package com.ine.backend.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ine.backend.entities.Education;
import com.ine.backend.entities.Laureat;

@Repository
public interface EducationRepository extends JpaRepository<Education, Long> {
	List<Education> findByLaureatOrderByStartYearDesc(Laureat laureat);
	List<Education> findByLaureatIdOrderByStartYearDesc(Long laureatId);
}
