package com.ine.backend.services;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ine.backend.dto.CountryLocationsDto;
import com.ine.backend.dto.LocationResponseDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import jakarta.annotation.PostConstruct;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Location service implementation.
 * 
 * Lifecycle:
 * 1. App starts → @PostConstruct init() runs
 * 2. Loads cities.json from resources/data/
 * 3. Caches data in memory (never reloaded)
 * 4. Pre-computes location strings for performance
 */
@Service
@Slf4j
public class LocationServiceImpl implements LocationService {

    private final ObjectMapper objectMapper;

    // Cached data (loaded once at startup)
    private Map<String, List<String>> locationsData;      // {"Morocco": ["Casablanca", ...]}
    private List<String> locationStrings;                  // ["Remote", "Casablanca, Morocco", ...]
    private Set<String> locationSet;                       // For O(1) validation

    public LocationServiceImpl(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    /**
     * Load location data at startup.
     * Called automatically by Spring after bean creation.
     */
    @PostConstruct
    public void init() {
        try {
            // Load JSON file from src/main/resources/data/cities.json
            ClassPathResource resource = new ClassPathResource("data/cities.json");
            
            // Parse JSON: {"Morocco": ["Casablanca", "Rabat"], ...}
            locationsData = objectMapper.readValue(
                resource.getInputStream(),
                new TypeReference<Map<String, List<String>>>() {}
            );
            
            // Pre-compute location strings
            locationStrings = buildLocationStrings();
            locationSet = new HashSet<>(locationStrings);
            
            log.info("Loaded {} countries, {} total locations",
                    locationsData.size(), locationStrings.size());
                    
        } catch (IOException e) {
            log.error("Failed to load cities.json", e);
            // Initialize with empty data to prevent crashes
            locationsData = new HashMap<>();
            locationStrings = Arrays.asList("Remote", "Hybrid");
            locationSet = new HashSet<>(locationStrings);
        }
    }

    @Override
    public LocationResponseDto getAllLocations() {
        // Convert map to list of DTOs
        List<CountryLocationsDto> countries = locationsData.entrySet().stream()
                .map(entry -> CountryLocationsDto.builder()
                        .country(entry.getKey())
                        .cities(entry.getValue())
                        .build())
                .sorted(Comparator.comparing(CountryLocationsDto::getCountry))
                .collect(Collectors.toList());

        // Calculate totals
        int totalCities = locationsData.values().stream()
                .mapToInt(List::size)
                .sum();

        return LocationResponseDto.builder()
                .countries(countries)
                .locationStrings(locationStrings)
                .totalCountries(locationsData.size())
                .totalCities(totalCities)
                .build();
    }

    @Override
    public List<String> getAllLocationStrings() {
        return locationStrings;  // Return cached list
    }

    @Override
    public List<String> getAllCountries() {
        return locationsData.keySet().stream()
                .sorted()
                .collect(Collectors.toList());
    }

    @Override
    public List<String> getCitiesByCountry(String country) {
        if (country == null || country.trim().isEmpty()) {
            return Collections.emptyList();
        }
        return locationsData.getOrDefault(country, Collections.emptyList());
    }

    @Override
    public List<String> searchLocations(String query) {
        // Return all if no query
        if (query == null || query.trim().isEmpty()) {
            return locationStrings;
        }

        // Filter by query (case-insensitive)
        String lowerQuery = query.toLowerCase().trim();
        return locationStrings.stream()
                .filter(loc -> loc.toLowerCase().contains(lowerQuery))
                .limit(50)  // Max 50 results
                .collect(Collectors.toList());
    }

    @Override
    public boolean isValidLocation(String location) {
        if (location == null || location.trim().isEmpty()) {
            return false;
        }
        return locationSet.contains(location);  // O(1) lookup
    }

    /**
     * Build flat list of location strings.
     * Format: "City, Country"
     * Special locations: "Remote", "Hybrid"
     */
    private List<String> buildLocationStrings() {
        List<String> locations = new ArrayList<>();

        // Add special locations first
        locations.add("Remote");
        locations.add("Hybrid");

        // Build "City, Country" for all cities
        for (Map.Entry<String, List<String>> entry : locationsData.entrySet()) {
            String country = entry.getKey();
            for (String city : entry.getValue()) {
                locations.add(String.format("%s, %s", city, country));
            }
        }

        // Sort (keep Remote/Hybrid at top)
        List<String> special = locations.stream()
                .filter(loc -> loc.equals("Remote") || loc.equals("Hybrid"))
                .collect(Collectors.toList());
        
        List<String> regular = locations.stream()
                .filter(loc -> !loc.equals("Remote") && !loc.equals("Hybrid"))
                .sorted()
                .collect(Collectors.toList());
        
        special.addAll(regular);
        return special;
    }
}