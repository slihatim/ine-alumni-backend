package com.ine.backend.dto;

import com.ine.backend.entities.OfferType;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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

    // FIXED: Changed back to String with custom deserializer to handle JSON input properly
    @NotBlank(message = "Offer type is required")
    private String type;

    private String customType;

    private String duration;

    @NotBlank
    private String description;

    private String link;

    // FIXED: Back to string-based validation since JSON comes as string
    @AssertTrue(message = "customType is required when type is 'OTHER'")
    public boolean isCustomTypeValid() {
        if (type == null) return true;
        boolean isOther = "other".equalsIgnoreCase(type);
        return !isOther || (customType != null && !customType.isBlank());
    }

    // Helper method to get enum from string
    public OfferType getOfferTypeEnum() {
        return OfferType.fromString(this.type);
    }
}