package ru.bironix.super_food.dtos.interfaces.portion;

import ru.bironix.super_food.dtos.dish.PortionDto;

import java.util.List;

public interface CreateUpdateForActionPortions {

    List<PortionDto.CreateUpdateForAction> getPortions();
}
