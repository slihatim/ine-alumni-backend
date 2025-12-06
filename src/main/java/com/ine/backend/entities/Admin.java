package com.ine.backend.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "admins")
@Getter
@Setter
@SuperBuilder
public class Admin extends User {
	public Admin() {
		super.setRole(Role.ROLE_ADMIN);
		super.setIsEmailVerified(true);
		super.setIsAccountVerified(true);
	}
}
