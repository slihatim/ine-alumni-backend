package com.ine.backend.entities;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.*;

@Entity
@DiscriminatorValue("INE")
@Setter
@Getter
// Ine users
public class Ine extends InptUser {
}
