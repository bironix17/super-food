package ru.bironix.super_food.store.db.models.person;

import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Set;


public enum Role {
    ROLE_CLIENT,
    ROLE_ADMIN,
    ROLE_DELIVERYMAN,
    ROLE_COOK,
    ROLE_MANAGER;

    public Set<SimpleGrantedAuthority> getAuthority() {
        return Set.of(new SimpleGrantedAuthority(this.name()));
    }
}
