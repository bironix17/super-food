package ru.bironix.super_food.dtos.interfaces.portion;

import com.fasterxml.jackson.annotation.JsonAlias;
import ru.bironix.super_food.dtos.dish.PortionDto;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;


public interface BindPortions {

    @Valid
    @NotNull
    List<PortionDto.Bind> getPortions();
}
