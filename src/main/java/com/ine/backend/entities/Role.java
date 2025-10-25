package com.ine.backend.entities;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.security.core.authority.SimpleGrantedAuthority;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum Role {
	// Student can read resources and events
	ROLE_USER(Set.of(Permission.EVENTS_READ, Permission.RESOURCES_READ)),

	// Admin can manage resources and events (except some super admin actions)
	ROLE_ADMIN(Set.of(Permission.EVENTS_READ, Permission.EVENTS_CREATE, Permission.EVENTS_UPDATE,
			Permission.EVENTS_UPDATE_SELF, Permission.EVENTS_DELETE_SELF, Permission.RESOURCES_READ,
			Permission.RESOURCES_CREATE, Permission.RESOURCES_UPDATE, Permission.RESOURCES_UPDATE_SELF,
			Permission.RESOURCES_DELETE, Permission.RESOURCES_DELETE_SELF)),

	// Super admin has all permissions
	ROLE_SUPER_ADMIN(Set.of(Permission.EVENTS_READ, Permission.EVENTS_CREATE, Permission.EVENTS_UPDATE,
			Permission.EVENTS_UPDATE_SELF, Permission.EVENTS_DELETE, Permission.EVENTS_DELETE_SELF,
			Permission.RESOURCES_READ, Permission.RESOURCES_CREATE, Permission.RESOURCES_UPDATE,
			Permission.RESOURCES_UPDATE_SELF, Permission.RESOURCES_DELETE, Permission.RESOURCES_DELETE_SELF,
			Permission.ADMIN_CREATE, Permission.ADMIN_READ, Permission.ADMIN_UPDATE, Permission.ADMIN_DELETE));

	@Getter
	private final Set<Permission> permissions;

	public List<SimpleGrantedAuthority> getAuthorities() {
		var authorities = getPermissions().stream()
				.map(permission -> new SimpleGrantedAuthority(permission.getPermission())).collect(Collectors.toList());

		authorities.add(new SimpleGrantedAuthority(this.name()));
		return authorities;
	}
}
