package ru.bironix.super_food.dtos.interfaces;

import ru.bironix.super_food.dtos.person.AddressDto;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

public interface Address {
    @Valid
    @NotEmpty
    AddressDto getAddress();
}
