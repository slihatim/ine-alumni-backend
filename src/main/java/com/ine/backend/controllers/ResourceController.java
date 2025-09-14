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

import com.ine.backend.dto.ApiResponseDto;
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
	public ResponseEntity<ApiResponseDto<Resource>> createResource(@Valid @RequestBody Resource resource) {
		try {
			log.info("Attempting to create new resource with title: {}", resource.getTitle());
			Resource savedResource = resourceService.createResource(resource);
			log.info("Successfully created resource with ID: {} and title: {}", savedResource.getId(),
					savedResource.getTitle());
			return new ResponseEntity<>(ApiResponseDto.<Resource>builder().message("Resource created")
					.response(savedResource).isSuccess(true).build(), HttpStatus.CREATED);
		} catch (Exception e) {
			log.error("Failed to create resource with title: {}. Error: {}",
					resource != null ? resource.getTitle() : "null", e.getMessage(), e);
			return new ResponseEntity<>(
					ApiResponseDto.<Resource>builder().message(e.getMessage()).response(null).isSuccess(false).build(),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	// Get all resources
	@GetMapping
	@PreAuthorize("hasAuthority('resources:read')")
	public ResponseEntity<ApiResponseDto<List<Resource>>> getAllResources() {
		try {
			log.debug("Fetching all resources");
			List<Resource> resources = resourceService.getAllResources();
			if (resources.isEmpty()) {
				log.info("No resources found in database - returning empty result");
				return new ResponseEntity<>(ApiResponseDto.<List<Resource>>builder().message("No resources")
						.response(resources).isSuccess(true).build(), HttpStatus.OK);
			}
			log.info("Successfully retrieved {} resources", resources.size());
			return new ResponseEntity<>(
					ApiResponseDto.<List<Resource>>builder().message("OK").response(resources).isSuccess(true).build(),
					HttpStatus.OK);
		} catch (Exception e) {
			log.error("Failed to retrieve all resources from database. Error: {}", e.getMessage(), e);
			return new ResponseEntity<>(ApiResponseDto.<List<Resource>>builder().message(e.getMessage()).response(null)
					.isSuccess(false).build(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	// Get resource by ID
	@GetMapping("/{id}")
	@PreAuthorize("hasAuthority('resources:read')")
	public ResponseEntity<ApiResponseDto<Resource>> getResourceById(@PathVariable("id") Long id) {
		try {
			log.debug("Attempting to fetch resource with ID: {}", id);
			Optional<Resource> resource = resourceService.getResourceById(id);
			if (resource.isPresent()) {
				log.info("Successfully found resource with ID: {} and title: {}", id, resource.get().getTitle());
				return new ResponseEntity<>(ApiResponseDto.<Resource>builder().message("OK").response(resource.get())
						.isSuccess(true).build(), HttpStatus.OK);
			} else {
				log.warn("Resource with ID: {} not found in database", id);
				return new ResponseEntity<>(
						ApiResponseDto.<Resource>builder().message("Not found").response(null).isSuccess(false).build(),
						HttpStatus.NOT_FOUND);
			}
		} catch (Exception e) {
			log.error("Failed to retrieve resource with ID: {}. Error: {}", id, e.getMessage(), e);
			return new ResponseEntity<>(
					ApiResponseDto.<Resource>builder().message(e.getMessage()).response(null).isSuccess(false).build(),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	// Update resource
	@PutMapping("/{id}")
	@PreAuthorize("hasAuthority('resources:update') or hasAuthority('resources:update:self')")
	public ResponseEntity<ApiResponseDto<Resource>> updateResource(@PathVariable("id") Long id,
			@Valid @RequestBody Resource resource) {
		try {
			log.info("Attempting to update resource with ID: {} with new title: {}", id, resource.getTitle());
			Resource updatedResource = resourceService.updateResource(id, resource);
			log.info("Successfully updated resource with ID: {}. New title: {}", id, updatedResource.getTitle());
			return new ResponseEntity<>(ApiResponseDto.<Resource>builder().message("Resource updated")
					.response(updatedResource).isSuccess(true).build(), HttpStatus.OK);
		} catch (RuntimeException e) {
			log.error("Resource with ID: {} not found for update. Error: {}", id, e.getMessage(), e);
			return new ResponseEntity<>(
					ApiResponseDto.<Resource>builder().message(e.getMessage()).response(null).isSuccess(false).build(),
					HttpStatus.NOT_FOUND);
		} catch (Exception e) {
			log.error("Failed to update resource with ID: {} due to unexpected error. Error: {}", id, e.getMessage(),
					e);
			return new ResponseEntity<>(
					ApiResponseDto.<Resource>builder().message(e.getMessage()).response(null).isSuccess(false).build(),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	// Delete resource
	@DeleteMapping("/{id}")
	@PreAuthorize("hasAuthority('resources:delete') or hasAuthority('resources:delete:self')")
	public ResponseEntity<ApiResponseDto<Void>> deleteResource(@PathVariable("id") Long id) {
		try {
			log.info("Attempting to delete resource with ID: {}", id);
			resourceService.deleteResource(id);
			log.info("Successfully deleted resource with ID: {}", id);
			return new ResponseEntity<>(
					ApiResponseDto.<Void>builder().message("Deleted").response(null).isSuccess(true).build(),
					HttpStatus.OK);
		} catch (RuntimeException e) {
			log.error("Resource with ID: {} not found for deletion. Error: {}", id, e.getMessage(), e);
			return new ResponseEntity<>(
					ApiResponseDto.<Void>builder().message(e.getMessage()).response(null).isSuccess(false).build(),
					HttpStatus.NOT_FOUND);
		} catch (Exception e) {
			log.error("Failed to delete resource with ID: {} due to unexpected error. Error: {}", id, e.getMessage(),
					e);
			return new ResponseEntity<>(
					ApiResponseDto.<Void>builder().message(e.getMessage()).response(null).isSuccess(false).build(),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	// Get resources by category
	@GetMapping("/category/{category}")
	@PreAuthorize("hasAuthority('resources:read')")
	public ResponseEntity<ApiResponseDto<List<Resource>>> getResourcesByCategory(
			@PathVariable("category") String category) {
		try {
			log.debug("Attempting to fetch resources for category: {}", category);
			Category cat = Category.valueOf(category.toUpperCase());
			List<Resource> resources = resourceService.getResourcesByCategory(cat);
			if (resources.isEmpty()) {
				log.info("No resources found for category: {} - returning empty result", category);
				return new ResponseEntity<>(ApiResponseDto.<List<Resource>>builder().message("No resources")
						.response(resources).isSuccess(true).build(), HttpStatus.OK);
			}
			log.info("Successfully retrieved {} resources for category: {}", resources.size(), category);
			return new ResponseEntity<>(
					ApiResponseDto.<List<Resource>>builder().message("OK").response(resources).isSuccess(true).build(),
					HttpStatus.OK);
		} catch (IllegalArgumentException iae) {
			log.warn("Invalid category provided: {}. Available categories should be from Category enum", category);
			return new ResponseEntity<>(ApiResponseDto.<List<Resource>>builder().message("Invalid category")
					.response(null).isSuccess(false).build(), HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			log.error("Failed to retrieve resources for category: {}. Error: {}", category, e.getMessage(), e);
			return new ResponseEntity<>(ApiResponseDto.<List<Resource>>builder().message(e.getMessage()).response(null)
					.isSuccess(false).build(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	// Get resources by domain
	@GetMapping("/domain/{domain}")
	@PreAuthorize("hasAuthority('resources:read')")
	public ResponseEntity<ApiResponseDto<List<Resource>>> getResourcesByDomain(@PathVariable("domain") String domain) {
		try {
			log.debug("Attempting to fetch resources for domain: {}", domain);
			Domain d = Domain.valueOf(domain.toUpperCase());
			List<Resource> resources = resourceService.getResourcesByDomain(d);
			if (resources.isEmpty()) {
				log.info("No resources found for domain: {} - returning empty result", domain);
				return new ResponseEntity<>(ApiResponseDto.<List<Resource>>builder().message("No resources")
						.response(resources).isSuccess(true).build(), HttpStatus.OK);
			}
			log.info("Successfully retrieved {} resources for domain: {}", resources.size(), domain);
			return new ResponseEntity<>(
					ApiResponseDto.<List<Resource>>builder().message("OK").response(resources).isSuccess(true).build(),
					HttpStatus.OK);
		} catch (IllegalArgumentException iae) {
			log.warn("Invalid domain provided: {}. Available domains should be from Domain enum", domain);
			return new ResponseEntity<>(ApiResponseDto.<List<Resource>>builder().message("Invalid domain")
					.response(null).isSuccess(false).build(), HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			log.error("Failed to retrieve resources for domain: {}. Error: {}", domain, e.getMessage(), e);
			return new ResponseEntity<>(ApiResponseDto.<List<Resource>>builder().message(e.getMessage()).response(null)
					.isSuccess(false).build(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	// Filter resources by category and domain
	@GetMapping("/filter")
	@PreAuthorize("hasAuthority('resources:read')")
	public ResponseEntity<ApiResponseDto<List<Resource>>> filterResources(
			@RequestParam(required = false) String category, @RequestParam(required = false) String domain) {
		try {
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
				return new ResponseEntity<>(ApiResponseDto.<List<Resource>>builder().message("No resources")
						.response(resources).isSuccess(true).build(), HttpStatus.OK);
			}
			log.info("Successfully filtered {} resources with category: {} and domain: {}", resources.size(), category,
					domain);
			return new ResponseEntity<>(
					ApiResponseDto.<List<Resource>>builder().message("OK").response(resources).isSuccess(true).build(),
					HttpStatus.OK);
		} catch (IllegalArgumentException iae) {
			log.warn("Invalid filter parameters provided - category: {}, domain: {}. Error: {}", category, domain,
					iae.getMessage());
			return new ResponseEntity<>(ApiResponseDto.<List<Resource>>builder().message("Invalid filter params")
					.response(null).isSuccess(false).build(), HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			log.error("Failed to filter resources with category: {} and domain: {}. Error: {}", category, domain,
					e.getMessage(), e);
			return new ResponseEntity<>(ApiResponseDto.<List<Resource>>builder().message(e.getMessage()).response(null)
					.isSuccess(false).build(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	// Search resources by keyword
	@GetMapping("/search")
	@PreAuthorize("hasAuthority('resources:read')")
	public ResponseEntity<ApiResponseDto<List<Resource>>> searchResources(@RequestParam("q") String keyword) {
		try {
			log.debug("Attempting to search resources with keyword: {}", keyword);
			List<Resource> resources = resourceService.searchResources(keyword);
			if (resources.isEmpty()) {
				log.info("No resources found matching search keyword: {} - returning empty result", keyword);
				return new ResponseEntity<>(ApiResponseDto.<List<Resource>>builder().message("No resources")
						.response(resources).isSuccess(true).build(), HttpStatus.OK);
			}
			log.info("Successfully found {} resources matching search keyword: {}", resources.size(), keyword);
			return new ResponseEntity<>(
					ApiResponseDto.<List<Resource>>builder().message("OK").response(resources).isSuccess(true).build(),
					HttpStatus.OK);
		} catch (Exception e) {
			log.error("Failed to search resources with keyword: {}. Error: {}", keyword, e.getMessage(), e);
			return new ResponseEntity<>(ApiResponseDto.<List<Resource>>builder().message(e.getMessage()).response(null)
					.isSuccess(false).build(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	// Get all available categories
	@GetMapping("/categories")
	@PreAuthorize("hasAuthority('resources:read')")
	public ResponseEntity<ApiResponseDto<List<Category>>> getAllCategories() {
		try {
			log.debug("Fetching all available categories from Category enum");
			List<Category> categories = resourceService.getAllCategories();
			log.info("Successfully retrieved {} available categories", categories.size());
			return new ResponseEntity<>(
					ApiResponseDto.<List<Category>>builder().message("OK").response(categories).isSuccess(true).build(),
					HttpStatus.OK);
		} catch (Exception e) {
			log.error("Failed to retrieve available categories. Error: {}", e.getMessage(), e);
			return new ResponseEntity<>(ApiResponseDto.<List<Category>>builder().message(e.getMessage()).response(null)
					.isSuccess(false).build(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	// Get all available domains
	@GetMapping("/domains")
	@PreAuthorize("hasAuthority('resources:read')")
	public ResponseEntity<ApiResponseDto<List<Domain>>> getAllDomains() {
		try {
			log.debug("Fetching all available domains from Domain enum");
			List<Domain> domains = resourceService.getAllDomains();
			log.info("Successfully retrieved {} available domains", domains.size());
			return new ResponseEntity<>(
					ApiResponseDto.<List<Domain>>builder().message("OK").response(domains).isSuccess(true).build(),
					HttpStatus.OK);
		} catch (Exception e) {
			log.error("Failed to retrieve available domains. Error: {}", e.getMessage(), e);
			return new ResponseEntity<>(ApiResponseDto.<List<Domain>>builder().message(e.getMessage()).response(null)
					.isSuccess(false).build(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	// Get resources by author TBD

}
