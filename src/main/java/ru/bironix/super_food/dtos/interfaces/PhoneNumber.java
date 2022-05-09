package ru.bironix.super_food.dtos.interfaces;

import javax.validation.constraints.NotBlank;

public interface PhoneNumber {
    @NotBlank
    String getPhoneNumber();
}
