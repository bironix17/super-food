package ru.bironix.super_food.security;

public enum Permission {
    All("ALL");

    private final String permission;

    Permission(String permission) {
        this.permission = permission;
    }

    public String getPermission() {
        return permission;
    }
}
