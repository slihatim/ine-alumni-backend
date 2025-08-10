package com.ine.backend.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "offer_types", indexes = {
        @Index(name = "idx_offer_type_name", columnList = "name"),
        @Index(name = "idx_offer_type_name_custom", columnList = "name, custom_type")
})
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OfferType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Allowed values (lowercase suggested): job, internship, alternance, benevolat, other
     */
    @Column(name = "name", nullable = false, length = 50)
    @NotBlank
    private String name;

    /**
     * Optional label used only when name == "other"
     */
    @Column(name = "custom_type")
    private String customType;

    @OneToMany(mappedBy = "type", fetch = FetchType.LAZY)
    @Builder.Default
    @JsonIgnore
    private List<Offer> offers = new ArrayList<>();
}


