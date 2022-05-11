package ru.bironix.super_food.db.models.person;

import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Set;

public enum Role {
    CLIENT,
    ADMIN,
    DELIVERYMAN,
    COOK;

    public Set<SimpleGrantedAuthority> getAuthority() {
        return Set.of(new SimpleGrantedAuthority(this.name()));
    }
}
