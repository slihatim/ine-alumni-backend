package com.ine.backend.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ine.backend.entities.Skill;
import com.ine.backend.entities.SkillCategory;

@Repository
public interface SkillRepository extends JpaRepository<Skill, Long> {
	List<Skill> findByLaureatId(Long laureatId);
	List<Skill> findByLaureatIdAndCategory(Long laureatId, SkillCategory category);

	@Query("SELECT s.name FROM Skill s WHERE s.laureat.id = :laureatId")
	List<String> findSkillNamesByLaureatId(@Param("laureatId") Long laureatId);

	@Query("SELECT s.name, COUNT(s) FROM Skill s GROUP BY s.name ORDER BY COUNT(s) DESC")
	List<Object[]> findTopSkills();
}
