package com.ine.backend.controllers;

import com.ine.backend.entities.Resource;
import com.ine.backend.services.ResourceServiceImpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/resources")
@PreAuthorize("hasRole('STUDENT') or hasRole('GRADUATE')")
public class ResourceController {

    @Autowired
    private ResourceServiceImpl resourceService;

    // Create a new resource
    @PostMapping
    public ResponseEntity<Resource> createResource(@Valid @RequestBody Resource resource) {
        try {
            Resource savedResource = resourceService.createResource(resource);
            return new ResponseEntity<>(savedResource, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Get all resources
    @GetMapping
    public ResponseEntity<List<Resource>> getAllResources() {
        try {
            List<Resource> resources = resourceService.getAllResources();
            if (resources.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(resources, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Get resource by ID
    @GetMapping("/{id}")
    public ResponseEntity<Resource> getResourceById(@PathVariable("id") Long id) {
        Optional<Resource> resource = resourceService.getResourceById(id);
        if (resource.isPresent()) {
            return new ResponseEntity<>(resource.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Update resource
    @PutMapping("/{id}")
    public ResponseEntity<Resource> updateResource(@PathVariable("id") Long id,
                                                   @Valid @RequestBody Resource resource) {
        try {
            Resource updatedResource = resourceService.updateResource(id, resource);
            return new ResponseEntity<>(updatedResource, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Delete resource
    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteResource(@PathVariable("id") Long id) {
        try {
            resourceService.deleteResource(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Get resources by category
    @GetMapping("/category/{category}")
    public ResponseEntity<List<Resource>> getResourcesByCategory(@PathVariable("category") String category) {
        try {
            List<Resource> resources = resourceService.getResourcesByCategory(category);
            if (resources.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(resources, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Get resources by domain
    @GetMapping("/domain/{domain}")
    public ResponseEntity<List<Resource>> getResourcesByDomain(@PathVariable("domain") String domain) {
        try {
            List<Resource> resources = resourceService.getResourcesByDomain(domain);
            if (resources.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(resources, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Filter resources by category and domain
    @GetMapping("/filter")
    public ResponseEntity<List<Resource>> filterResources(
            @RequestParam(required = false) String category,
            @RequestParam(required = false) String domain) {
        try {
            List<Resource> resources;

            if (category != null && domain != null) {
                resources = resourceService.getResourcesByCategoryAndDomain(category, domain);
            } else if (category != null) {
                resources = resourceService.getResourcesByCategory(category);
            } else if (domain != null) {
                resources = resourceService.getResourcesByDomain(domain);
            } else {
                resources = resourceService.getAllResources();
            }

            if (resources.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(resources, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Search resources by keyword
    @GetMapping("/search")
    public ResponseEntity<List<Resource>> searchResources(@RequestParam("q") String keyword) {
        try {
            List<Resource> resources = resourceService.searchResources(keyword);
            if (resources.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(resources, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Get all available categories
    @GetMapping("/categories")
    public ResponseEntity<List<String>> getAllCategories() {
        try {
            List<String> categories = resourceService.getAllCategories();
            return new ResponseEntity<>(categories, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Get all available domains
    @GetMapping("/domains")
    public ResponseEntity<List<String>> getAllDomains() {
        try {
            List<String> domains = resourceService.getAllDomains();
            return new ResponseEntity<>(domains, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Get resources by author
    @GetMapping("/author/{author}")
    public ResponseEntity<List<Resource>> getResourcesByAuthor(@PathVariable("author") String author) {
        try {
            List<Resource> resources = resourceService.getResourcesByAuthor(author);
            if (resources.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(resources, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
