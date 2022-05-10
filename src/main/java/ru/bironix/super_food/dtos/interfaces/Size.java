package ru.bironix.super_food.dtos.interfaces;

import javax.validation.constraints.NotBlank;

public interface Size {
    @NotBlank
    String getSize();
}
