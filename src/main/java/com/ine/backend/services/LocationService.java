package com.ine.backend.services;

import com.ine.backend.dto.LocationResponseDto;

import java.util.List;

/**
 * Service for managing location data (countries and cities).
 * Loads data from JSON file at startup and provides query methods.
 */
public interface LocationService {
    
    // Get all location data (structured + flat list)
    LocationResponseDto getAllLocations();
    
    // Get flat list: ["Remote", "Casablanca, Morocco", ...]
    List<String> getAllLocationStrings();
    
    // Get all countries: ["Morocco", "France", ...]
    List<String> getAllCountries();
    
    // Get cities for a country: ["Casablanca", "Rabat", ...]
    List<String> getCitiesByCountry(String country);
    
    // Search locations by query (case-insensitive, partial match)
    List<String> searchLocations(String query);
    
    // Check if location string is valid
    boolean isValidLocation(String location);
}