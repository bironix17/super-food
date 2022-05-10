package ru.bironix.super_food.dtos.interfaces.portion;

import com.fasterxml.jackson.annotation.JsonAlias;
import ru.bironix.super_food.dtos.dish.PortionDto;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;


public interface BindPortion {

    @Valid
    @NotNull
    @JsonAlias("basePortion")
    PortionDto.Bind getPortion();
}
