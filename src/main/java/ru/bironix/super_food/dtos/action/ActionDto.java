package ru.bironix.super_food.dtos.action;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import ru.bironix.super_food.dtos.common.PicturePathsDto;
import ru.bironix.super_food.dtos.dish.DishDto;
import ru.bironix.super_food.dtos.dish.PortionDto;
import ru.bironix.super_food.dtos.interfaces.Id;
import ru.bironix.super_food.dtos.interfaces.Name;
import ru.bironix.super_food.dtos.interfaces.PicturePaths;
import ru.bironix.super_food.dtos.interfaces.dish.BindDishes;
import ru.bironix.super_food.dtos.interfaces.dish.Dishes;
import ru.bironix.super_food.dtos.interfaces.portion.CreateUpdateForActionPortions;

import java.util.List;

public abstract class ActionDto {

    @Schema(description = "Акция. Создание, обновление", name = "ActionDto.CreateUpdate")
    @Data
    @SuperBuilder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class CreateUpdate implements Name, PicturePaths, BindDishes, CreateUpdateForActionPortions {
        PicturePathsDto picturePaths;
        String name;
        List<DishDto.Bind> dishes;
        List<PortionDto.CreateUpdateForAction> portions;
    }


    public abstract static class Base {

        @Schema(description = "Акция. Базовая сжатая", name = "ActionDto.Base.Small")
        @Data
        @SuperBuilder
        @AllArgsConstructor
        @NoArgsConstructor
        public static class Small implements Id, Name, PicturePaths {
            Integer id;
            PicturePathsDto picturePaths;
            String name;
        }

        @Schema(description = "Акция. Базовая полная", name = "ActionDto.Base.Full")
        @Data
        @EqualsAndHashCode(callSuper = true)
        @SuperBuilder
        @AllArgsConstructor
        @NoArgsConstructor
        public static class Full extends Small implements Dishes {
            List<DishDto.Base.Small> dishes;
        }
    }
}