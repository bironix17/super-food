package ru.bironix.super_food.dtos.interfaces.price;

import ru.bironix.super_food.dtos.dish.PriceDto;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

public interface CreateUpdatePrice {
    @Valid
    @NotNull
    PriceDto.CreateUpdate getPrice();
}
