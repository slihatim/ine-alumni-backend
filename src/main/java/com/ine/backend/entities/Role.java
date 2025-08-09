package com.ine.backend.entities;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.security.core.authority.SimpleGrantedAuthority;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum Role {
  ROLE_USER(Set.of(Permission.EVENTS_READ)),
  ROLE_ADMIN(
      Set.of(
          Permission.EVENTS_READ,
          Permission.EVENTS_CREATE,
          Permission.EVENTS_UPDATE,
          Permission.EVENTS_UPDATE_SELF,
          Permission.EVENTS_DELETE_SELF)),
  ROLE_SUPER_ADMIN(
      Set.of(
          Permission.EVENTS_READ,
          Permission.EVENTS_CREATE,
          Permission.EVENTS_UPDATE,
          Permission.EVENTS_UPDATE_SELF,
          Permission.EVENTS_DELETE,
          Permission.EVENTS_DELETE_SELF)),
  // for example BDE, can only update and delete his own events that he created
  ROLE_BDE(
      Set.of(
          Permission.EVENTS_READ,
          Permission.EVENTS_CREATE,
          Permission.EVENTS_UPDATE_SELF,
          Permission.EVENTS_DELETE_SELF));

  @Getter private final Set<Permission> permissions;

  public List<SimpleGrantedAuthority> getAuthorities() {
    var authorities =
        getPermissions().stream()
            .map(permission -> new SimpleGrantedAuthority(permission.getPermission()))
            .collect(Collectors.toList());

    authorities.add(new SimpleGrantedAuthority(this.name()));
    return authorities;
  }
}
