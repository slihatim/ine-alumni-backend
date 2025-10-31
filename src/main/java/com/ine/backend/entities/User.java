package com.ine.backend.entities;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

@MappedSuperclass
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@SuperBuilder
// User Class will handle just authentication logic
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotBlank(message = "Le nom complet ne doit pas être vide.")
	private String fullName;

	@Email(message = "Email address is not valid.")
	@NotBlank(message = "Email address is required.")
	@Column(unique = true, nullable = false)
	private String email;

	@NotBlank(message = "Password is required.")
	@Column(nullable = false)
	@Size(min = 8, message = "Password must be at least 8 characters long.")
	@JsonIgnore
	private String password;

	@CreationTimestamp
	@Column(updatable = false)
	private LocalDateTime createdAt;

	@UpdateTimestamp
	private LocalDateTime updatedAt;

	@NotNull(message = "User role is required.")
	@Column(nullable = false)
	@Enumerated(value = EnumType.STRING)
	@Builder.Default
	private Role role = Role.ROLE_USER;

	// for INE : Ine Mail verification
	// for LAUREAT : Admin Approval verification
	// for Third Party Users : other logic maybe
	@Builder.Default
	private Boolean isAccountVerified = true;

	// Personal email verification
	@Builder.Default
	private Boolean isEmailVerified = false;

	// for OAuth (Will be discussed in upcomming versions)
	private String linkedinId;
	@Builder.Default
	private Boolean isOauthAccount = false;
}
