package com.ine.backend.entities;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.*;

@Entity
@DiscriminatorValue("LAUREAT")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Setter
@Getter
// Laureat users
public class Laureat extends InptUser{
    private Company currentCompany;
}
