package ru.bironix.super_food.security;

import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Set;

import static java.util.stream.Collectors.toSet;

public enum Role {
    User(Set.of(Permission.All));

    private final Set<Permission> permissions;

    Role(Set<Permission> permissions) {
        this.permissions = permissions;
    }

    public Set<SimpleGrantedAuthority> getAuthorities() {
        return permissions.stream()
                .map(p -> new SimpleGrantedAuthority(p.getPermission()))
                .collect(toSet());
    }
}
