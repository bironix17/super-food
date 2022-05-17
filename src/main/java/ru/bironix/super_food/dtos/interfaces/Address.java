package ru.bironix.super_food.dtos.interfaces;

import ru.bironix.super_food.dtos.person.AddressDto;

import javax.validation.Valid;

public interface Address {
    @Valid
    AddressDto getAddress();
}
