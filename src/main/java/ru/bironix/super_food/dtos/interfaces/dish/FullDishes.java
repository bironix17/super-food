package ru.bironix.super_food.dtos.interfaces.dish;

import ru.bironix.super_food.dtos.dish.DishDto;

import javax.validation.Valid;
import java.util.List;

public interface FullDishes {

    @Valid
    List<DishDto.Base.Full> getDishes();
}
