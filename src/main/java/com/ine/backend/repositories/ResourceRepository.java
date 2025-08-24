package com.ine.backend.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ine.backend.entities.Category;
import com.ine.backend.entities.Domain;
import com.ine.backend.entities.InptUser;
import com.ine.backend.entities.Resource;

@Repository
public interface ResourceRepository extends JpaRepository<Resource, Long> {

	List<Resource> findByCategory(Category category);

	List<Resource> findByDomain(Domain domain);

	List<Resource> findByCategoryAndDomain(Category category, Domain domain);

	List<Resource> findByAuthor(InptUser author);

	// This Query is used to improve searching it applies the search on 3 fields
	// which are title, description and author
	@Query("SELECT r FROM Resource r WHERE " + "LOWER(r.title) LIKE LOWER(CONCAT('%', :keyword, '%')) OR "
			+ "LOWER(r.description) LIKE LOWER(CONCAT('%', :keyword, '%')) OR "
			+ "LOWER(r.author) LIKE LOWER(CONCAT('%', :keyword, '%'))")
	List<Resource> searchByKeyword(@Param("keyword") String keyword);

	@Query("SELECT DISTINCT r.category FROM Resource r ORDER BY r.category")
	List<Category> findAllCategories();

	@Query("SELECT DISTINCT r.domain FROM Resource r ORDER BY r.domain")
	List<Domain> findAllDomains();
}
