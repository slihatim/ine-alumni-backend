package com.ine.backend.controllers;

import com.ine.backend.dto.ApiResponseDto;
import com.ine.backend.dto.LocationResponseDto;
import com.ine.backend.dto.LocationValidationRequest;
import com.ine.backend.services.LocationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST Controller for location endpoints.
 * 
 * Endpoints:
 * - GET  /api/v1/locations                          → All location data
 * - GET  /api/v1/locations/strings                  → Flat list (for dropdowns)
 * - GET  /api/v1/locations/search?q={query}         → Search locations
 * - GET  /api/v1/locations/countries                → All countries
 * - GET  /api/v1/locations/countries/{country}/cities → Cities by country
 * - POST /api/v1/locations/validate                 → Validate location
 */
@RestController
@RequestMapping("/api/v1/locations")
@RequiredArgsConstructor
@Slf4j
@CrossOrigin(origins = "*")  // Allow all origins (configure as needed)
public class LocationController {

    private final LocationService locationService;

    /**
     * GET /api/v1/locations
     * Returns complete location data (structured + flat)
     */
    @GetMapping
    public ResponseEntity<ApiResponseDto<LocationResponseDto>> getAllLocations() {
        try {
            LocationResponseDto locations = locationService.getAllLocations();
            return ResponseEntity.ok(
                ApiResponseDto.<LocationResponseDto>builder()
                    .message("OK")
                    .response(locations)
                    .isSuccess(true)
                    .build()
            );
        } catch (Exception e) {
            log.error("Failed to get locations", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ApiResponseDto.<LocationResponseDto>builder()
                    .message(e.getMessage())
                    .response(null)
                    .isSuccess(false)
                    .build()
                );
        }
    }

    /**
     * GET /api/v1/locations/strings
     * Returns: ["Remote", "Hybrid", "Casablanca, Morocco", ...]
     * Primary endpoint for dropdowns
     */
    @GetMapping("/strings")
    public ResponseEntity<ApiResponseDto<List<String>>> getLocationStrings() {
        try {
            List<String> locations = locationService.getAllLocationStrings();
            return ResponseEntity.ok(
                ApiResponseDto.<List<String>>builder()
                    .message("OK")
                    .response(locations)
                    .isSuccess(true)
                    .build()
            );
        } catch (Exception e) {
            log.error("Failed to get location strings", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ApiResponseDto.<List<String>>builder()
                    .message(e.getMessage())
                    .response(null)
                    .isSuccess(false)
                    .build()
                );
        }
    }

    /**
     * GET /api/v1/locations/search?q={query}
     * Search locations (case-insensitive, partial match)
     * Example: ?q=casa → ["Casablanca, Morocco"]
     */
    @GetMapping("/search")
    public ResponseEntity<ApiResponseDto<List<String>>> searchLocations(
            @RequestParam(value = "q", required = false) String query) {
        try {
            List<String> results = locationService.searchLocations(query);
            return ResponseEntity.ok(
                ApiResponseDto.<List<String>>builder()
                    .message("OK")
                    .response(results)
                    .isSuccess(true)
                    .build()
            );
        } catch (Exception e) {
            log.error("Failed to search locations", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ApiResponseDto.<List<String>>builder()
                    .message(e.getMessage())
                    .response(null)
                    .isSuccess(false)
                    .build()
                );
        }
    }

    /**
     * GET /api/v1/locations/countries
     * Returns: ["Afghanistan", "Albania", "Morocco", ...]
     */
    @GetMapping("/countries")
    public ResponseEntity<ApiResponseDto<List<String>>> getAllCountries() {
        try {
            List<String> countries = locationService.getAllCountries();
            return ResponseEntity.ok(
                ApiResponseDto.<List<String>>builder()
                    .message("OK")
                    .response(countries)
                    .isSuccess(true)
                    .build()
            );
        } catch (Exception e) {
            log.error("Failed to get countries", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ApiResponseDto.<List<String>>builder()
                    .message(e.getMessage())
                    .response(null)
                    .isSuccess(false)
                    .build()
                );
        }
    }

    /**
     * GET /api/v1/locations/countries/{country}/cities
     * Example: /countries/Morocco/cities → ["Casablanca", "Rabat", ...]
     */
    @GetMapping("/countries/{country}/cities")
    public ResponseEntity<ApiResponseDto<List<String>>> getCitiesByCountry(
            @PathVariable("country") String country) {
        try {
            List<String> cities = locationService.getCitiesByCountry(country);
            
            if (cities.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ApiResponseDto.<List<String>>builder()
                        .message("Country not found: " + country)
                        .response(null)
                        .isSuccess(false)
                        .build()
                    );
            }
            
            return ResponseEntity.ok(
                ApiResponseDto.<List<String>>builder()
                    .message("OK")
                    .response(cities)
                    .isSuccess(true)
                    .build()
            );
        } catch (Exception e) {
            log.error("Failed to get cities for country: {}", country, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ApiResponseDto.<List<String>>builder()
                    .message(e.getMessage())
                    .response(null)
                    .isSuccess(false)
                    .build()
                );
        }
    }

    /**
     * POST /api/v1/locations/validate
     * Body: { "location": "Casablanca, Morocco" }
     * Returns: { "response": true/false }
     */
    @PostMapping("/validate")
    public ResponseEntity<ApiResponseDto<Boolean>> validateLocation(
            @RequestBody LocationValidationRequest request) {
        try {
            boolean isValid = locationService.isValidLocation(request.getLocation());
            return ResponseEntity.ok(
                ApiResponseDto.<Boolean>builder()
                    .message(isValid ? "Valid location" : "Invalid location")
                    .response(isValid)
                    .isSuccess(true)
                    .build()
            );
        } catch (Exception e) {
            log.error("Failed to validate location", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ApiResponseDto.<Boolean>builder()
                    .message(e.getMessage())
                    .response(false)
                    .isSuccess(false)
                    .build()
                );
        }
    }
}
