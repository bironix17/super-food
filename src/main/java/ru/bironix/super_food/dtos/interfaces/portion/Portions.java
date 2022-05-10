package ru.bironix.super_food.dtos.interfaces.portion;

import ru.bironix.super_food.dtos.dish.PortionDto;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.util.List;


public interface Portions {

    @Valid
    @NotEmpty
    List<PortionDto.Base> getPortions();
}
