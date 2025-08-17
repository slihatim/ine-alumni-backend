package com.ine.backend.services;


import com.ine.backend.entities.Resource;
import com.ine.backend.exceptions.ResourceNotFoundException;
import com.ine.backend.repositories.ResourceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ResourceServiceImpl implements ResourceService {

    @Autowired
    private ResourceRepository resourceRepository;

    @Override
    public Resource createResource(Resource resource) {
        validateResource(resource);
        resource.setCreatedDate(LocalDateTime.now());
        return resourceRepository.save(resource);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<Resource> getResourceById(Long id) {
        if (id == null) {
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
            throw new IllegalArgumentException("Resource ID cannot be null");
        }

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
    }

    @Override
    public void deleteResource(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("Resource ID cannot be null");
        }

        Resource resource = resourceRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Resource not found with id: " + id));

        resourceRepository.delete(resource);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Resource> getResourcesByCategory(String category) {
        if (category == null || category.trim().isEmpty()) {
            throw new IllegalArgumentException("Category cannot be null or empty");
        }
        return resourceRepository.findByCategory(category.trim());
    }

    @Override
    @Transactional(readOnly = true)
    public List<Resource> getResourcesByDomain(String domain) {
        if (domain == null || domain.trim().isEmpty()) {
            throw new IllegalArgumentException("Domain cannot be null or empty");
        }
        return resourceRepository.findByDomain(domain.trim());
    }

    @Override
    @Transactional(readOnly = true)
    public List<Resource> getResourcesByCategoryAndDomain(String category, String domain) {
        if (category == null || category.trim().isEmpty()) {
            throw new IllegalArgumentException("Category cannot be null or empty");
        }
        if (domain == null || domain.trim().isEmpty()) {
            throw new IllegalArgumentException("Domain cannot be null or empty");
        }
        return resourceRepository.findByCategoryAndDomain(category.trim(), domain.trim());
    }

    @Override
    @Transactional(readOnly = true)
    public List<Resource> searchResources(String keyword) {
        if (keyword == null || keyword.trim().isEmpty()) {
            throw new IllegalArgumentException("Search keyword cannot be null or empty");
        }
        return resourceRepository.searchByKeyword(keyword.trim());
    }

    @Override
    @Transactional(readOnly = true)
    public List<String> getAllCategories() {
        return resourceRepository.findAllCategories();
    }

    @Override
    @Transactional(readOnly = true)
    public List<String> getAllDomains() {
        return resourceRepository.findAllDomains();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Resource> getResourcesByAuthor(String author) {
        if (author == null || author.trim().isEmpty()) {
            throw new IllegalArgumentException("Author cannot be null or empty");
        }
        return resourceRepository.findByAuthor(author.trim());
    }

    /**
     * Validate resource data
     * @param resource the resource to validate
     */
    private void validateResource(Resource resource) {
        if (resource == null) {
            throw new IllegalArgumentException("Resource cannot be null");
        }

        if (resource.getTitle() == null || resource.getTitle().trim().isEmpty()) {
            throw new IllegalArgumentException("Resource title cannot be null or empty");
        }

        if (resource.getCategory() == null || resource.getCategory().trim().isEmpty()) {
            throw new IllegalArgumentException("Resource category cannot be null or empty");
        }

        if (resource.getLink() == null || resource.getLink().trim().isEmpty()) {
            throw new IllegalArgumentException("Resource link cannot be null or empty");
        }

        if (resource.getIsConfidential() == null) {
            throw new IllegalArgumentException("Resource link cannot be null or empty");
        }

        if (resource.getAuthor() == null || resource.getAuthor().trim().isEmpty()) {
            throw new IllegalArgumentException("Resource author cannot be null or empty");
        }

        if (resource.getDomain() == null || resource.getDomain().trim().isEmpty()) {
            throw new IllegalArgumentException("Resource domain cannot be null or empty");
        }

        if (resource.getCreatedDate() == null) {
            throw new IllegalArgumentException("Resource date cannot be null");
        }
    }
}

