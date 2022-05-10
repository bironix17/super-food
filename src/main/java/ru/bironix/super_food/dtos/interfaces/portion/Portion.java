package ru.bironix.super_food.dtos.interfaces.portion;

import ru.bironix.super_food.dtos.dish.PortionDto;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;


public interface Portion {

    @Valid
    @NotNull
    PortionDto.Base getPortion();
}
