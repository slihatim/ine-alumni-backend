package com.ine.backend.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ine.backend.entities.ExternalLink;
import com.ine.backend.entities.LinkType;

@Repository
public interface ExternalLinkRepository extends JpaRepository<ExternalLink, Long> {
	List<ExternalLink> findByLaureatId(Long laureatId);
	List<ExternalLink> findByCompanyId(Long companyId);
	List<ExternalLink> findByLaureatIdAndLinkType(Long laureatId, LinkType linkType);
}
