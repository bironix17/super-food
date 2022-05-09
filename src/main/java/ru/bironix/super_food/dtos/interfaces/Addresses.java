package ru.bironix.super_food.dtos.interfaces;

import ru.bironix.super_food.dtos.person.AddressDto;

import javax.validation.Valid;
import java.util.List;

public interface Addresses {
    @Valid
    List<AddressDto> getAddresses();
}
