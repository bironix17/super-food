package ru.bironix.super_food.dtos.interfaces.price;

import io.swagger.v3.oas.annotations.media.Schema;
import ru.bironix.super_food.dtos.dish.PriceDto;

import javax.validation.Valid;

public interface OldPrice {
    @Schema(description = "если есть скидка, то не null", nullable = true)
    @Valid
    PriceDto.Base getOldPrice();
}
