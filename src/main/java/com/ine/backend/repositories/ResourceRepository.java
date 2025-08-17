package com.ine.backend.repositories;

import com.ine.backend.entities.Resource;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ResourceRepository extends JpaRepository<Resource, Long> {

    List<Resource> findByCategory(String category);

    List<Resource> findByDomain(String domain);

    List<Resource> findByCategoryAndDomain(String category, String domain);

    List<Resource> findByAuthor(String author);

    // This Query is used to improve searching it applies the search on 3 fields which are title, description and author
    @Query("SELECT r FROM Resource r WHERE " +
            "LOWER(r.title) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(r.description) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(r.author) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    List<Resource> searchByKeyword(@Param("keyword") String keyword);

    @Query("SELECT DISTINCT r.category FROM Resource r ORDER BY r.category")
    List<String> findAllCategories();

    @Query("SELECT DISTINCT r.domain FROM Resource r ORDER BY r.domain")
    List<String> findAllDomains();
}
