package com.ine.backend.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@DiscriminatorValue("LAUREAT")
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
// Laureat users
public class Laureat extends InptUser {
  @ManyToOne
  @JoinColumn(name = "current_company_id")
  private Company currentCompany;
}
