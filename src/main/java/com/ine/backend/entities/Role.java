package com.ine.backend.entities;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.security.core.authority.SimpleGrantedAuthority;

import lombok.Getter;

public enum Role {
	// Student can read resources, events and manage their own profile
	ROLE_USER(buildUserPermissions()),

	// Admin inherits all USER permissions + can manage resources, events and
	// read/update all profiles
	ROLE_ADMIN(buildAdminPermissions()),

	// Super admin inherits all ADMIN permissions + can delete profiles and manage
	// admins
	ROLE_SUPER_ADMIN(buildSuperAdminPermissions());

	@Getter
	private final Set<Permission> permissions;

	Role(Set<Permission> permissions) {
		this.permissions = permissions;
	}

	/**
	 * Build USER permissions - basic permissions for regular users
	 */
	private static Set<Permission> buildUserPermissions() {
		Set<Permission> userPermissions = new HashSet<>();

		// Add basic permissions for events
		userPermissions.add(Permission.EVENTS_READ);

		// Add basic permissions for resources
		userPermissions.add(Permission.RESOURCES_READ);

		// Add basic permissions for profiles
		userPermissions.add(Permission.PROFILE_READ);
		userPermissions.add(Permission.PROFILE_UPDATE);

		return userPermissions;
	}

	/**
	 * Build ADMIN permissions by inheriting from USER and adding admin-specific
	 * permissions
	 */
	private static Set<Permission> buildAdminPermissions() {
		Set<Permission> adminPermissions = new HashSet<>(ROLE_USER.permissions);

		// Add admin-specific permissions for events
		adminPermissions.add(Permission.EVENTS_CREATE);
		adminPermissions.add(Permission.EVENTS_UPDATE);
		adminPermissions.add(Permission.EVENTS_UPDATE_SELF);
		adminPermissions.add(Permission.EVENTS_DELETE_SELF);

		// Add admin-specific permissions for resources
		adminPermissions.add(Permission.RESOURCES_CREATE);
		adminPermissions.add(Permission.RESOURCES_UPDATE);
		adminPermissions.add(Permission.RESOURCES_UPDATE_SELF);
		adminPermissions.add(Permission.RESOURCES_DELETE);
		adminPermissions.add(Permission.RESOURCES_DELETE_SELF);

		// Add admin-specific permissions for profiles
		adminPermissions.add(Permission.PROFILE_READ_ALL);
		adminPermissions.add(Permission.PROFILE_UPDATE_ALL);

		return adminPermissions;
	}

	/**
	 * Build SUPER_ADMIN permissions by inheriting from ADMIN and adding
	 * super-admin-specific permissions
	 */
	private static Set<Permission> buildSuperAdminPermissions() {
		Set<Permission> superAdminPermissions = new HashSet<>(ROLE_ADMIN.permissions);

		// Add super admin-specific permissions for events (full delete)
		superAdminPermissions.add(Permission.EVENTS_DELETE);

		// Add super admin-specific permissions for profiles (delete)
		superAdminPermissions.add(Permission.PROFILE_DELETE_ALL);

		// Add super admin-specific permissions for admin management
		superAdminPermissions.add(Permission.ADMIN_CREATE);
		superAdminPermissions.add(Permission.ADMIN_READ);
		superAdminPermissions.add(Permission.ADMIN_UPDATE);
		superAdminPermissions.add(Permission.ADMIN_DELETE);

		return superAdminPermissions;
	}

	public List<SimpleGrantedAuthority> getAuthorities() {
		var authorities = getPermissions().stream()
				.map(permission -> new SimpleGrantedAuthority(permission.getPermission())).collect(Collectors.toList());

		authorities.add(new SimpleGrantedAuthority(this.name()));
		return authorities;
	}
}
