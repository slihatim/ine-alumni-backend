package com.ine.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * API response DTO for location endpoints.
 * Contains both structured data (countries) and flat list (location strings).
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LocationResponseDto {
    
    // Structured: [{ country: "Morocco", cities: [...] }, ...]
    private List<CountryLocationsDto> countries;
    
    // Flat list: ["Remote", "Hybrid", "Casablanca, Morocco", ...]
    // This is what dropdowns will use
    private List<String> locationStrings;
    
    // Metadata
    private int totalCountries;
    private int totalCities;
}