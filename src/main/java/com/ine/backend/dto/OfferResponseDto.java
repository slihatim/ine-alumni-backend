package com.ine.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OfferResponseDto {
    private Long id;
    private String title;
    private String company;
    private String location;
    private String type;
    private String customType;
    private String duration;
    private String description;
    private String link;
    private LocalDateTime postedAt;
}


