package com.ine.backend.entities;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Entity
@DiscriminatorValue("ine")
@Setter
@Getter
@SuperBuilder
@NoArgsConstructor
// Ine users
public class Ine extends InptUser {
}
