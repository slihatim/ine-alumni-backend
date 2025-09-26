package com.ine.backend.services;

import java.util.List;
import java.util.Optional;

import com.ine.backend.entities.Category;
import com.ine.backend.entities.Domain;
import com.ine.backend.entities.InptUser;
import com.ine.backend.entities.Resource;

public interface ResourceService {

	/**
	 * Create a new resource
	 *
	 * @param resource
	 *            the resource to create
	 * @return the created resource
	 */
	Resource createResource(Resource resource);

	/**
	 * Get resource by ID
	 *
	 * @param id
	 *            the resource ID
	 * @return Optional containing the resource if found
	 */
	Optional<Resource> getResourceById(Long id);

	/**
	 * Get all resources
	 *
	 * @return list of all resources
	 */
	List<Resource> getAllResources();

	/**
	 * Update an existing resource
	 *
	 * @param id
	 *            the resource ID to update
	 * @param resourceDetails
	 *            the updated resource details
	 * @return the updated resource
	 */
	Resource updateResource(Long id, Resource resourceDetails);

	/**
	 * Delete resource by ID
	 *
	 * @param id
	 *            the resource ID to delete
	 */
	void deleteResource(Long id);

	/**
	 * Get resources filtered by category
	 *
	 * @param category
	 *            the category to filter by
	 * @return list of resources in the specified category
	 */
	List<Resource> getResourcesByCategory(Category category);

	/**
	 * Get resources filtered by domain
	 *
	 * @param domain
	 *            the domain to filter by
	 * @return list of resources in the specified domain
	 */
	List<Resource> getResourcesByDomain(Domain domain);

	/**
	 * Get resources filtered by both category and domain
	 *
	 * @param category
	 *            the category to filter by
	 * @param domain
	 *            the domain to filter by
	 * @return list of resources matching both criteria
	 */
	List<Resource> getResourcesByCategoryAndDomain(Category category, Domain domain);

	/**
	 * Search resources by keyword
	 *
	 * @param keyword
	 *            the keyword to search for
	 * @return list of resources matching the keyword
	 */
	List<Resource> searchResources(String keyword);

	/**
	 * Get all available categories
	 *
	 * @return list of all distinct categories
	 */
	List<Category> getAllCategories();

	/**
	 * Get all available domains
	 *
	 * @return list of all distinct domains
	 */
	List<Domain> getAllDomains();

	/**
	 * Get resources filtered by author
	 *
	 * @param author
	 *            the author to filter by
	 * @return list of resources by the specified author
	 */
	List<Resource> getResourcesByAuthor(InptUser author);
}
