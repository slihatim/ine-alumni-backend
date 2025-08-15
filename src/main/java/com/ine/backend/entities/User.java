package com.ine.backend.entities;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Setter
@Getter
@MappedSuperclass
// User Class will handle just authentication logic
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Email(message = "L'adresse email n'est pas valide.")
	@NotBlank(message = "L'adresse email est obligatoire.")
	@Column(unique = true, nullable = false)
	private String email;

	@NotBlank(message = "Le mot de passe est obligatoire.")
	@Column(nullable = false)
	@Size(min = 8, message = "Le mot de passe doit contenir au moins 8 caractères.")
	private String password;

	@CreationTimestamp
	@Column(updatable = false)
	private LocalDateTime createdAt;

	@UpdateTimestamp
	private LocalDateTime updatedAt;

	@NotNull(message = "Le rôle de l'utilisateur est obligatoire.")
	@Column(nullable = false)
	@Enumerated(value = EnumType.STRING)
	@Builder.Default
	private Role role = Role.ROLE_USER;

	// for INE : Ine Mail verification
	// for LAUREAT : Admin Approval verification
	// for Third Party Users : other logic maybe
	@Builder.Default
	private Boolean isAccountVerified = false;

	// for OAuth (Will be discussed in upcomming versions)
	private String linkedinId;
	@Builder.Default
	private Boolean isOauthAccount = false;
}
