package com.ine.backend.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.validator.constraints.URL;

import java.time.LocalDate;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Setter
@Getter
@Entity
@Table(name = "ines")
public class INE {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Le nom complet ne doit pas être vide.")
    private String fullName;

    @Email(message = "L'adresse email n'est pas valide.")
    @NotBlank(message = "L'adresse email est obligatoire.")
    @Column(unique = true, nullable = false)
    private String email;

    @NotBlank(message = "Le mot de passe est obligatoire.")
    @Column(nullable = false)
    @Size(min = 8, message = "Le mot de passe doit contenir au moins 8 caractères.")
    private String password;

    @NotNull(message = "La filière est obligatoire.")
    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private Major major;

    @NotNull(message = "L'année de votre promotion est obligatoire.")
    @Min(value=1961, message = "L'année de promo doit être supérieure ou égale à 1961.")
    @Max(value=2200, message = "L'année de promo doit être inférieure ou égale à 2200.")
    @Column(nullable = false)
    private Integer graduationYear;

    private String phoneNumber;
    private LocalDate birthDate;
    @Enumerated(value = EnumType.STRING)
    private Gender gender;
    @URL(message = "Le lien LinkedIn doit être une URL valide.")
    private String linkedinUrl;
    @URL(message = "Le lien de la photo de profil doit être une URL valide.")
    private String profilePhotoUrl;
    private String country;
    private String city;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @NotNull(message = "Le rôle de l'utilisateur est obligatoire.")
    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private Role role = Role.ROLE_USER;

    // for OAuth
    private String linkedinId;
    private Boolean isOauthAccount = false;

    private Boolean isEmailVerified = false;
}
