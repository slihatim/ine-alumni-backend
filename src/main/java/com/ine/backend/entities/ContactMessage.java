package com.ine.backend.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ContactMessage {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotBlank
	private String nom;
	@NotBlank
	private String prenom;
	@NotBlank
	@Email
	private String email;
	@NotBlank
	private String objet;

	@Column(length = 1000)
	@NotBlank
	private String message;
}
