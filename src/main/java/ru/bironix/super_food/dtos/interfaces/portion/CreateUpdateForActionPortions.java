package ru.bironix.super_food.dtos.interfaces.portion;

import ru.bironix.super_food.dtos.dish.PortionDto;

import javax.validation.constraints.NotNull;
import java.util.List;

public interface CreateUpdateForActionPortions {

    @NotNull List<PortionDto.CreateUpdateForAction> getPortions();
}
