package ru.bironix.super_food.dtos.interfaces;

import ru.bironix.super_food.dtos.person.PersonDto;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

public interface BindClient {
    @Valid
    @NotNull
    PersonDto.Bind getClient();
}
