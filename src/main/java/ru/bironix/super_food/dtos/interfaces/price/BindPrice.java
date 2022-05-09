package ru.bironix.super_food.dtos.interfaces.price;

import com.fasterxml.jackson.annotation.JsonAlias;
import ru.bironix.super_food.dtos.dish.PriceDto;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

public interface BindPrice {
    @Valid
    @NotNull
    @JsonAlias("priceNow")
    PriceDto.Bind getPrice();
}
