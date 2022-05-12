package ru.bironix.super_food.dtos.dish;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import ru.bironix.super_food.dtos.common.PicturePathsDto;
import ru.bironix.super_food.dtos.interfaces.*;
import ru.bironix.super_food.dtos.interfaces.addon.Addons;
import ru.bironix.super_food.dtos.interfaces.addon.BindAddons;
import ru.bironix.super_food.dtos.interfaces.dish.BindDishes;
import ru.bironix.super_food.dtos.interfaces.dish.Dishes;
import ru.bironix.super_food.dtos.interfaces.portion.*;

import java.util.List;

public abstract class DishDto {


    @Schema(description = "Блюдо. Создание, обновление", name = "DishDto.CreateUpdate")
    @Data
    @SuperBuilder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class CreateUpdate implements PicturePaths, Name, Composition,
            CategoryType, Deleted, CreateUpdateBasePortion, Description, Allergens,
            CreateUpdatePortions, BindAddons, BindDishes {

        PicturePathsDto picturePaths;
        String name;
        String composition;
        CategoryTypeDto category;
        Boolean deleted = false;
        PortionDto.CreateUpdate basePortion;
        String description;
        String allergens;
        List<PortionDto.CreateUpdate> portions;
        List<AddonDto.Bind> addons;
        List<DishDto.Bind> dishes;
    }

    public static class Base {

        @Schema(description = "Блюдо. Базовая сжатая", name = "DishDto.Base.Small")
        @Data
        @SuperBuilder
        @AllArgsConstructor
        @NoArgsConstructor
        public static class Small implements Id, PicturePaths, Name, Composition,
                CategoryType, Deleted, BasePortion {

            Integer id;
            PicturePathsDto picturePaths;
            String name;
            String composition;
            CategoryTypeDto category;
            Boolean deleted;
            PortionDto.Base basePortion;
        }

        @Schema(description = "Блюдо. Базовая полная", name = "DishDto.Base.Full")
        @Data
        @SuperBuilder
        @AllArgsConstructor
        @NoArgsConstructor
        public static class Full extends Small implements Description, Allergens,
                Portions, Addons, Dishes {
            String description;
            String allergens;
            List<PortionDto.Base> portions;
            List<AddonDto.Base> addons;
            List<DishDto.Base.Small> dishes;
        }
    }

    @Schema(description = "Блюдо. Связующая", name = "DishDto.Bind")
    @Data
    @SuperBuilder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Bind implements Id, BindPortion, BindAddons {
        Integer id;
        PortionDto.Bind portion;
        List<AddonDto.Bind> addons;
    }
}
