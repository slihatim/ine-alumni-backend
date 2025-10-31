package com.ine.backend.entities;

import java.time.LocalDate;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "inpt_users")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "user_type", discriminatorType = DiscriminatorType.STRING)
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@SuperBuilder
// will handle both Ine and Laureat users
// It will be one table on the DB that has both Ine fields and Laureat fields
// with a user_type field that differenciate between ines and laureats
public class InptUser extends User {

	@NotNull(message = "La filière est obligatoire.")
	@Column(nullable = false)
	@Enumerated(value = EnumType.STRING)
	private Major major;

	@NotNull(message = "L'année de votre promotion est obligatoire.")
	@Min(value = 1961, message = "L'année de promo doit être supérieure ou égale à 1961.")
	@Max(value = 2200, message = "L'année de promo doit être inférieure ou égale à 2200.")
	@Column(nullable = false)
	private Integer graduationYear;

	private String phoneNumber;
	private LocalDate birthDate;

	@Enumerated(value = EnumType.STRING)
	@NotNull(message = "Le sexe est obligatoire.")
	private Gender gender;

	private String country;
	private String city;

	@Column(name = "user_type", insertable = false, updatable = false)
	private String userType;
}
