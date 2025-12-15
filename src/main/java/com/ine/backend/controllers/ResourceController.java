package com.ine.backend.controllers;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.ine.backend.entities.*;
import com.ine.backend.services.ResourceService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/resources")
public class ResourceController {

	private static final Logger log = LoggerFactory.getLogger(ResourceController.class);

	@Autowired
	private ResourceService resourceService;

	// Create a new resource
	@PostMapping
	@PreAuthorize("hasAuthority('resources:create')")
	public ResponseEntity<Resource> createResource(@Valid @RequestBody Resource resource) {
		log.info("Attempting to create new resource with title: {}", resource.getTitle());
		Resource savedResource = resourceService.createResource(resource);
		log.info("Successfully created resource with ID: {} and title: {}", savedResource.getId(),
				savedResource.getTitle());
		return ResponseEntity.status(HttpStatus.CREATED).body(savedResource);
	}

	// Get all resources
	@GetMapping
	@PreAuthorize("hasAuthority('resources:read')")
	public ResponseEntity<List<Resource>> getAllResources() {
		log.debug("Fetching all resources");
		List<Resource> resources = resourceService.getAllResources();
		if (resources.isEmpty()) {
			log.info("No resources found in database - returning empty result");
		} else {
			log.info("Successfully retrieved {} resources", resources.size());
		}
		return ResponseEntity.ok(resources);
	}

	// Get resource by ID
	@GetMapping("/{id}")
	@PreAuthorize("hasAuthority('resources:read')")
	public ResponseEntity<Resource> getResourceById(@PathVariable("id") Long id) {
		log.debug("Attempting to fetch resource with ID: {}", id);
		Optional<Resource> resource = resourceService.getResourceById(id);
		if (resource.isPresent()) {
			log.info("Successfully found resource with ID: {} and title: {}", id, resource.get().getTitle());
			return ResponseEntity.ok(resource.get());
		} else {
			log.warn("Resource with ID: {} not found in database", id);
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
	}

	// Update resource
	@PutMapping("/{id}")
	@PreAuthorize("hasAuthority('resources:update') or hasAuthority('resources:update:self')")
	public ResponseEntity<Resource> updateResource(@PathVariable("id") Long id, @Valid @RequestBody Resource resource) {
		log.info("Attempting to update resource with ID: {} with new title: {}", id, resource.getTitle());
		Resource updatedResource = resourceService.updateResource(id, resource);
		log.info("Successfully updated resource with ID: {}. New title: {}", id, updatedResource.getTitle());
		return ResponseEntity.ok(updatedResource);
	}

	// Delete resource
	@DeleteMapping("/{id}")
	@PreAuthorize("hasAuthority('resources:delete') or hasAuthority('resources:delete:self')")
	public ResponseEntity<Void> deleteResource(@PathVariable("id") Long id) {
		log.info("Attempting to delete resource with ID: {}", id);
		resourceService.deleteResource(id);
		log.info("Successfully deleted resource with ID: {}", id);
		return ResponseEntity.ok().build();
	}

	// Get resources by category
	@GetMapping("/category/{category}")
	@PreAuthorize("hasAuthority('resources:read')")
	public ResponseEntity<List<Resource>> getResourcesByCategory(@PathVariable("category") String category) {
		log.debug("Attempting to fetch resources for category: {}", category);
		Category cat = Category.valueOf(category.toUpperCase());
		List<Resource> resources = resourceService.getResourcesByCategory(cat);
		if (resources.isEmpty()) {
			log.info("No resources found for category: {} - returning empty result", category);
		} else {
			log.info("Successfully retrieved {} resources for category: {}", resources.size(), category);
		}
		return ResponseEntity.ok(resources);
	}

	// Get resources by domain
	@GetMapping("/domain/{domain}")
	@PreAuthorize("hasAuthority('resources:read')")
	public ResponseEntity<List<Resource>> getResourcesByDomain(@PathVariable("domain") String domain) {
		log.debug("Attempting to fetch resources for domain: {}", domain);
		Domain d = Domain.valueOf(domain.toUpperCase());
		List<Resource> resources = resourceService.getResourcesByDomain(d);
		if (resources.isEmpty()) {
			log.info("No resources found for domain: {} - returning empty result", domain);
		} else {
			log.info("Successfully retrieved {} resources for domain: {}", resources.size(), domain);
		}
		return ResponseEntity.ok(resources);
	}

	// Filter resources by category and domain
	@GetMapping("/filter")
	@PreAuthorize("hasAuthority('resources:read')")
	public ResponseEntity<List<Resource>> filterResources(@RequestParam(required = false) String category,
			@RequestParam(required = false) String domain) {
		log.debug("Attempting to filter resources with category: {} and domain: {}", category, domain);
		List<Resource> resources;

		if (category != null && domain != null) {
			log.debug("Filtering by both category: {} and domain: {}", category, domain);
			resources = resourceService.getResourcesByCategoryAndDomain(Category.valueOf(category.toUpperCase()),
					Domain.valueOf(domain.toUpperCase()));
		} else if (category != null) {
			log.debug("Filtering by category only: {}", category);
			resources = resourceService.getResourcesByCategory(Category.valueOf(category.toUpperCase()));
		} else if (domain != null) {
			log.debug("Filtering by domain only: {}", domain);
			resources = resourceService.getResourcesByDomain(Domain.valueOf(domain.toUpperCase()));
		} else {
			log.debug("No filters provided - returning all resources");
			resources = resourceService.getAllResources();
		}

		if (resources.isEmpty()) {
			log.info("No resources found matching filter criteria - category: {}, domain: {}", category, domain);
		} else {
			log.info("Successfully filtered {} resources with category: {} and domain: {}", resources.size(), category,
					domain);
		}
		return ResponseEntity.ok(resources);
	}

	// Search resources by keyword
	@GetMapping("/search")
	@PreAuthorize("hasAuthority('resources:read')")
	public ResponseEntity<List<Resource>> searchResources(@RequestParam("q") String keyword) {
		log.debug("Attempting to search resources with keyword: {}", keyword);
		List<Resource> resources = resourceService.searchResources(keyword);
		if (resources.isEmpty()) {
			log.info("No resources found matching search keyword: {} - returning empty result", keyword);
		} else {
			log.info("Successfully found {} resources matching search keyword: {}", resources.size(), keyword);
		}
		return ResponseEntity.ok(resources);
	}

	// Get all available categories
	@GetMapping("/categories")
	@PreAuthorize("hasAuthority('resources:read')")
	public ResponseEntity<List<Category>> getAllCategories() {
		log.debug("Fetching all available categories from Category enum");
		List<Category> categories = resourceService.getAllCategories();
		log.info("Successfully retrieved {} available categories", categories.size());
		return ResponseEntity.ok(categories);
	}

	// Get all available domains
	@GetMapping("/domains")
	@PreAuthorize("hasAuthority('resources:read')")
	public ResponseEntity<List<Domain>> getAllDomains() {
		log.debug("Fetching all available domains from Domain enum");
		List<Domain> domains = resourceService.getAllDomains();
		log.info("Successfully retrieved {} available domains", domains.size());
		return ResponseEntity.ok(domains);
	}

	// Get resources by author TBD

}
