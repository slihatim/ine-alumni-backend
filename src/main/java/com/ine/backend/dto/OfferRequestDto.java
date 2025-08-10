package com.ine.backend.dto;

import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OfferRequestDto {

    @NotBlank
    private String title;

    @NotBlank
    private String company;

    @NotBlank
    private String location;

    @NotBlank
    @Pattern(regexp = "(?i)^(job|internship|alternance|benevolat|other)$",
             message = "type must be one of: job, internship, alternance, benevolat, other")
    private String type;

    private String customType;

    private String duration;

    @NotBlank
    private String description;

    private String link;

    @AssertTrue(message = "customType is required when type is 'other'")
    public boolean isCustomTypeValid() {
        if (type == null) return true;
        boolean isOther = "other".equalsIgnoreCase(type);
        return !isOther || (customType != null && !customType.isBlank());
    }
}


