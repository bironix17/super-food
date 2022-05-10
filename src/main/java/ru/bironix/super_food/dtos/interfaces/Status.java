package ru.bironix.super_food.dtos.interfaces;

import io.swagger.v3.oas.annotations.media.Schema;
import ru.bironix.super_food.dtos.order.OrderStatusDto;


public interface Status {

    @Schema(description = "Флаг удаления блюда")
    OrderStatusDto getStatus();
}
