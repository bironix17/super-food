package ru.bironix.super_food.dtos.interfaces;

import io.swagger.v3.oas.annotations.media.Schema;


public interface Deleted {

    @Schema(description = "Флаг удаления блюда")
    Boolean getDeleted();
}
