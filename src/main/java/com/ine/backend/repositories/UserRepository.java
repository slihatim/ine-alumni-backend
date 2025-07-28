package com.ine.backend.repositories;

import com.ine.backend.entities.InptUser;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<InptUser, Long> {
    InptUser findByEmail(@Email(message = "L'adresse email n'est pas valide.") @NotBlank(message = "L'adresse email est obligatoire.") String email);

    boolean existsByEmail(@Email(message = "L'adresse email n'est pas valide.") @NotBlank(message = "L'adresse email est obligatoire.") String email);
}
