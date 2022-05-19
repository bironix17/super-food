package ru.bironix.super_food.dtos.dish;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import ru.bironix.super_food.dtos.common.PicturePathsDto;
import ru.bironix.super_food.dtos.interfaces.*;
import ru.bironix.super_food.dtos.interfaces.addon.Addons;
import ru.bironix.super_food.dtos.interfaces.addon.BindAddons;
import ru.bironix.super_food.dtos.interfaces.addon.BindForOrderAddons;
import ru.bironix.super_food.dtos.interfaces.dish.BindDishes;
import ru.bironix.super_food.dtos.interfaces.dish.Dishes;
import ru.bironix.super_food.dtos.interfaces.portion.*;

import java.util.List;

public abstract class DishDto {


    @Schema(description = "Блюдо. Создание, обновление", name = "DishDto.Create")
    @Data
    @SuperBuilder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Create implements PicturePaths, Name, Composition,
            CategoryType, Deleted, CreateUpdateBasePortion, Description, Allergens,
            CreateUpdatePortions, BindAddons, BindDishes, EnergyValue, Protein, Fat, Carbohydrates {

        PicturePathsDto picturePaths;
        String name;
        String composition;
        String category;
        Boolean deleted = false;
        PortionDto.CreateUpdate basePortion;
        String description;
        String allergens;
        List<PortionDto.CreateUpdate> portions;
        List<AddonDto.Bind> addons;
        List<DishDto.Bind> dishes;
        Double energyValue;
        Double protein;
        Double fat;
        Double carbohydrates;
    }

    @Schema(description = "Блюдо. Обновление", name = "DishDto.Update")
    @Data
    @SuperBuilder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Update implements PicturePaths, Name, Composition,
            CategoryType, Deleted, BasePortion, Description, Allergens,
            Portions, BindAddons, BindDishes, EnergyValue, Protein, Fat, Carbohydrates {

        PicturePathsDto picturePaths;
        String name;
        String composition;
        String category;
        Boolean deleted = false;
        PortionDto.Base basePortion;
        String description;
        String allergens;
        List<PortionDto.Base> portions;
        List<AddonDto.Bind> addons;
        List<DishDto.Bind> dishes;
        Double energyValue;
        Double protein;
        Double fat;
        Double carbohydrates;
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
            String category;
            Boolean deleted;
            PortionDto.Base basePortion;
        }

        @Schema(description = "Блюдо. Базовая полная", name = "DishDto.Base.Full")
        @Data
        @SuperBuilder
        @AllArgsConstructor
        @NoArgsConstructor
        public static class Full extends Small implements Description, Allergens,
                Portions, Addons, Dishes, EnergyValue, Protein, Fat, Carbohydrates {
            String description;
            String allergens;
            List<PortionDto.Base> portions;
            List<AddonDto.Base> addons;
            List<DishDto.Base.Small> dishes;
            Double energyValue;
            Double protein;
            Double fat;
            Double carbohydrates;
        }

        @Schema(description = "Блюдо. Базовая для заказа", name = "DishDto.Base.ForOrder")
        @Data
        @SuperBuilder
        @AllArgsConstructor
        @NoArgsConstructor
        public static class ForOrder implements Id, PicturePaths, Name,
                CategoryType, Deleted, Portion, Addons, Dishes {

            Integer id;
            PicturePathsDto picturePaths;
            String name;
            String category;
            Boolean deleted;
            PortionDto.Base portion;
            List<AddonDto.Base> addons;
            List<DishDto.Base.Small> dishes;
        }
    }

    @Schema(description = "Блюдо. Связующая", name = "DishDto.Bind")
    @Data
    @SuperBuilder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Bind implements Id {
        Integer id;
    }

    @Schema(description = "Блюдо. Связующая для заказа", name = "DishDto.BindForOrder")
    @Data
    @SuperBuilder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class BindForOrder implements Id, BindPortion, BindForOrderAddons {
        Integer id;
        PortionDto.Bind portion;
        List<AddonDto.BindForOrder> addons;
    }
}
