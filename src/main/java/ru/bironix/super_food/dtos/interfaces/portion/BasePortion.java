package ru.bironix.super_food.dtos.interfaces.portion;

import io.swagger.v3.oas.annotations.media.Schema;
import ru.bironix.super_food.dtos.dish.PortionDto;

import javax.validation.constraints.NotNull;

public interface BasePortion {

    @Schema(description = "базовая порция блюда")
    @NotNull PortionDto.Base getBasePortion();
}
