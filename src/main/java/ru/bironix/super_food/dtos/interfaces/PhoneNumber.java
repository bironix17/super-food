package ru.bironix.super_food.dtos.interfaces;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

public interface PhoneNumber {
    @NotBlank
    @Pattern(regexp = "^(\\+7)[0-9]{10}$")
    String getPhoneNumber();
}
