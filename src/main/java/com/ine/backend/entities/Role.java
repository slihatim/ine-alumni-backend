package com.ine.backend.entities;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.security.core.authority.SimpleGrantedAuthority;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum Role {
	// Student can read resources, events and manage their own profile
	ROLE_USER(Set.of(Permission.EVENTS_READ, Permission.RESOURCES_READ, Permission.PROFILE_READ,
			Permission.PROFILE_UPDATE)),

	// Admin can manage resources, events and read/update all profiles (except
	// delete)
	ROLE_ADMIN(Set.of(Permission.EVENTS_READ, Permission.EVENTS_CREATE, Permission.EVENTS_UPDATE,
			Permission.EVENTS_UPDATE_SELF, Permission.EVENTS_DELETE_SELF, Permission.RESOURCES_READ,
			Permission.RESOURCES_CREATE, Permission.RESOURCES_UPDATE, Permission.RESOURCES_UPDATE_SELF,
			Permission.RESOURCES_DELETE, Permission.RESOURCES_DELETE_SELF, Permission.PROFILE_READ,
			Permission.PROFILE_READ_ALL, Permission.PROFILE_UPDATE, Permission.PROFILE_UPDATE_ALL)),

	// Super admin has all permissions including profile deletion
	ROLE_SUPER_ADMIN(Set.of(Permission.EVENTS_READ, Permission.EVENTS_CREATE, Permission.EVENTS_UPDATE,
			Permission.EVENTS_UPDATE_SELF, Permission.EVENTS_DELETE, Permission.EVENTS_DELETE_SELF,
			Permission.RESOURCES_READ, Permission.RESOURCES_CREATE, Permission.RESOURCES_UPDATE,
			Permission.RESOURCES_UPDATE_SELF, Permission.RESOURCES_DELETE, Permission.RESOURCES_DELETE_SELF,
			Permission.PROFILE_READ, Permission.PROFILE_READ_ALL, Permission.PROFILE_UPDATE,
			Permission.PROFILE_UPDATE_ALL, Permission.PROFILE_DELETE_ALL)),

	// BDE can manage events and their own profile
	ROLE_BDE(Set.of(Permission.EVENTS_READ, Permission.EVENTS_CREATE, Permission.EVENTS_UPDATE_SELF,
			Permission.EVENTS_DELETE_SELF, Permission.PROFILE_READ, Permission.PROFILE_UPDATE));

	@Getter
	private final Set<Permission> permissions;

	public List<SimpleGrantedAuthority> getAuthorities() {
		var authorities = getPermissions().stream()
				.map(permission -> new SimpleGrantedAuthority(permission.getPermission())).collect(Collectors.toList());

		authorities.add(new SimpleGrantedAuthority(this.name()));
		return authorities;
	}
}
