package ru.bironix.super_food.dtos.interfaces;

import javax.validation.constraints.NotBlank;

public interface Email {
    @NotBlank
    @javax.validation.constraints.Email
    String getEmail();
}
