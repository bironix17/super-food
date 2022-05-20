package ru.bironix.super_food.dtos.interfaces.dish;

import ru.bironix.super_food.dtos.dish.DishCountDto;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.util.List;

public interface CreateUpdateDishesCounts {

    @Valid
    @NotEmpty
    List<DishCountDto.CreteUpdate> getDishes();
}
