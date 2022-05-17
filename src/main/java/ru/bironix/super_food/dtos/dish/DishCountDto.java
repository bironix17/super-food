package ru.bironix.super_food.dtos.dish;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import ru.bironix.super_food.dtos.interfaces.Count;
import ru.bironix.super_food.dtos.interfaces.Id;
import ru.bironix.super_food.dtos.interfaces.addon.BindAddons;
import ru.bironix.super_food.dtos.interfaces.dish.*;
import ru.bironix.super_food.dtos.interfaces.portion.BindPortion;

import java.util.List;


public abstract class DishCountDto {

    @Schema(description = "Количество блюда", name = "DishCountDto.CreteUpdate")
    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class CreteUpdate implements Count, BindDishForOrder {
        Integer count;
        DishDto.BindForOrder dish;
    }


    @Schema(description = "Количество блюда", name = "DishCountDto.Base")
    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Base implements Count, ForOrderDish {
        Integer count;
        DishDto.Base.ForOrder dish;
    }


}