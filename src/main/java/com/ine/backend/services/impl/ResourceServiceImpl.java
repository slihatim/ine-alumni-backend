package com.ine.backend.services.impl;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ine.backend.entities.Category;
import com.ine.backend.entities.Domain;
import com.ine.backend.entities.InptUser;
import com.ine.backend.entities.Resource;
import com.ine.backend.exceptions.ResourceNotFoundException;
import com.ine.backend.repositories.ResourceRepository;
import com.ine.backend.services.ResourceService;

@Service
@Transactional
public class ResourceServiceImpl implements ResourceService {

	private static final Logger log = LoggerFactory.getLogger(ResourceServiceImpl.class);

	@Autowired
	private ResourceRepository resourceRepository;

	@Override
	public Resource createResource(Resource resource) {
		try {
			validateResource(resource);
			return resourceRepository.save(resource);
		} catch (Exception e) {
			log.error("Error creating resource: {}", e.getMessage(), e);
			throw e;
		}
	}

	@Override
	@Transactional(readOnly = true)
	public Optional<Resource> getResourceById(Long id) {
		if (id == null) {
			log.error("getResourceById called with null id");
			throw new IllegalArgumentException("Resource ID cannot be null");
		}
		return resourceRepository.findById(id);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Resource> getAllResources() {
		return resourceRepository.findAll();
	}

	@Override
	public Resource updateResource(Long id, Resource resourceDetails) {
		if (id == null) {
			log.error("updateResource called with null id");
			throw new IllegalArgumentException("Resource ID cannot be null");
		}

		try {
			validateResource(resourceDetails);

			Resource existingResource = resourceRepository.findById(id)
					.orElseThrow(() -> new ResourceNotFoundException("Resource not found with id: " + id));

			// Update fields
			existingResource.setTitle(resourceDetails.getTitle());
			existingResource.setDescription(resourceDetails.getDescription());
			existingResource.setCategory(resourceDetails.getCategory());
			existingResource.setLink(resourceDetails.getLink());
			existingResource.setAuthor(resourceDetails.getAuthor());
			existingResource.setIsConfidential(resourceDetails.getIsConfidential());
			existingResource.setDomain(resourceDetails.getDomain());

			return resourceRepository.save(existingResource);
		} catch (ResourceNotFoundException rnfe) {
			log.error("Resource not found when updating id {}: {}", id, rnfe.getMessage());
			throw rnfe;
		} catch (Exception e) {
			log.error("Error updating resource id {}: {}", id, e.getMessage(), e);
			throw e;
		}
	}

	@Override
	public void deleteResource(Long id) {
		if (id == null) {
			log.error("deleteResource called with null id");
			throw new IllegalArgumentException("Resource ID cannot be null");
		}

		try {
			Resource resource = resourceRepository.findById(id)
					.orElseThrow(() -> new ResourceNotFoundException("Resource not found with id: " + id));

			resourceRepository.delete(resource);
		} catch (ResourceNotFoundException rnfe) {
			log.error("Resource not found when deleting id {}: {}", id, rnfe.getMessage());
			throw rnfe;
		} catch (Exception e) {
			log.error("Error deleting resource id {}: {}", id, e.getMessage(), e);
			throw e;
		}
	}

	@Override
	@Transactional(readOnly = true)
	public List<Resource> getResourcesByCategory(Category category) {
		if (category == null) {
			log.error("getResourcesByCategory called with null category");
			throw new IllegalArgumentException("Category cannot be null");
		}
		return resourceRepository.findByCategory(category);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Resource> getResourcesByDomain(Domain domain) {
		if (domain == null) {
			log.error("getResourcesByDomain called with null domain");
			throw new IllegalArgumentException("Domain cannot be null");
		}
		return resourceRepository.findByDomain(domain);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Resource> getResourcesByCategoryAndDomain(Category category, Domain domain) {
		if (category == null) {
			log.error("getResourcesByCategoryAndDomain called with null category");
			throw new IllegalArgumentException("Category cannot be null");
		}
		if (domain == null) {
			log.error("getResourcesByCategoryAndDomain called with null domain");
			throw new IllegalArgumentException("Domain cannot be null");
		}
		return resourceRepository.findByCategoryAndDomain(category, domain);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Resource> searchResources(String keyword) {
		if (keyword == null || keyword.trim().isEmpty()) {
			log.error("searchResources called with null/empty keyword");
			throw new IllegalArgumentException("Search keyword cannot be null or empty");
		}
		return resourceRepository.searchByKeyword(keyword.trim());
	}

	@Override
	@Transactional(readOnly = true)
	public List<Category> getAllCategories() {
		return resourceRepository.findAllCategories();
	}

	@Override
	@Transactional(readOnly = true)
	public List<Domain> getAllDomains() {
		return resourceRepository.findAllDomains();
	}

	@Override
	@Transactional(readOnly = true)
	public List<Resource> getResourcesByAuthor(InptUser author) {
		if (author == null) {
			log.error("getResourcesByAuthor called with null author");
			throw new IllegalArgumentException("Author cannot be null");
		}
		return resourceRepository.findByAuthor(author);
	}

	/**
	 * Validate resource data
	 *
	 * @param resource
	 *            the resource to validate
	 */
	private void validateResource(Resource resource) {
		if (resource == null) {
			log.error("validateResource called with null resource");
			throw new IllegalArgumentException("Resource cannot be null");
		}

		if (resource.getTitle() == null || resource.getTitle().trim().isEmpty()) {
			log.error("validateResource: title is null/empty");
			throw new IllegalArgumentException("Resource title cannot be null or empty");
		}

		if (resource.getCategory() == null) {
			log.error("validateResource: category is null");
			throw new IllegalArgumentException("Resource category cannot be null or empty");
		}

		if (resource.getLink() == null || resource.getLink().trim().isEmpty()) {
			log.error("validateResource: link is null/empty");
			throw new IllegalArgumentException("Resource link cannot be null or empty");
		}

		if (resource.getIsConfidential() == null) {
			log.error("validateResource: isConfidential is null");
			throw new IllegalArgumentException("Resource confidentiality flag cannot be null");
		}

		if (resource.getAuthor() == null) {
			log.error("validateResource: author is null");
			throw new IllegalArgumentException("Resource author cannot be null or empty");
		}

		if (resource.getDomain() == null) {
			log.error("validateResource: domain is null");
			throw new IllegalArgumentException("Resource domain cannot be null or empty");
		}

		// created_date field will be automatically assigned a value
	}
}
