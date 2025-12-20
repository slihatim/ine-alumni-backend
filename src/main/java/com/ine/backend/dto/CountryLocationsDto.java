package com.ine.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Represents a country and its cities.
 * Example: {"country":"Morocco","cities":["Kenitra", "Rabat","Ouazzane", "Marrakech"]}
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CountryLocationsDto {
    
    private String country;        // Country name (e.g., "Morocco")
    private List<String> cities;   // List of cities in this country
}