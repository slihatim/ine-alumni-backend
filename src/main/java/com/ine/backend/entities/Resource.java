package com.ine.backend.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "Resource")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class Resource {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Title is required")
    @Column(nullable = false, length = 255)
    private String title;

    @Column(columnDefinition = "TEXT")
    private String description;

    @NotNull(message = "IsConfidential is required")
    @Column(nullable = false)
    private Boolean isConfidential;

    @NotBlank(message = "Category is required")
    @Column(nullable = false, length = 100)
    private String category; // formations, podcasts, documents, videos, etc.

    @NotBlank(message = "Link is required")
    @Column(nullable = false)
    private String link;

    @NotBlank(message = "Author is required")
    @Column(nullable = false, length = 255)
    private String author;

    // The field can be true in case we want to update a certain row
    @NotNull(message = "Date is required")
    @Column(nullable = true)
    private LocalDateTime createdDate;

    @NotBlank(message = "Domain is required")
    @Column(nullable = false, length = 100)
    private String domain;

//    WE MIGHT THINK LATER ABOUT ADDING THIS TWO FIELDS FOR EVERY ENTITY TO ENSURE PROPER LOGGING
//    @Column(name = "created_at")
//    private LocalDateTime createdAt;
//
//    @Column(name = "updated_at")
//    private LocalDateTime updatedAt;
}
