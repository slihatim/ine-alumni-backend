package com.ine.backend.entities;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Entity
@DiscriminatorValue("laureat")
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@SuperBuilder
// Laureat users
public class Laureat extends InptUser {
	@ManyToOne
	@JoinColumn(name = "current_company_id")
	private Company currentCompany;
}
