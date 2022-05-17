package ru.bironix.super_food.dtos.interfaces;

import ru.bironix.super_food.store.db.models.person.Role;

import javax.validation.Valid;

public interface PersonRole {
    @Valid
    Role getRole();
}
