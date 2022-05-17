package ru.bironix.super_food.dtos.interfaces.dish;

import ru.bironix.super_food.dtos.dish.DishDto;

import javax.validation.Valid;
import java.util.List;

public interface BindDishes {

    @Valid
    List<DishDto.Bind> getDishes();
}
