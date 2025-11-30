package com.ine.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Request DTO for validating location strings.
 * Used in POST /api/v1/locations/validate
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LocationValidationRequest {
    
    private String location;  // Location to validate (e.g., "Paris, France")
}