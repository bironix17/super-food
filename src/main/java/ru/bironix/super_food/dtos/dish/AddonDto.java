package ru.bironix.super_food.dtos.dish;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import ru.bironix.super_food.dtos.interfaces.Deleted;
import ru.bironix.super_food.dtos.interfaces.Id;
import ru.bironix.super_food.dtos.interfaces.Name;
import ru.bironix.super_food.dtos.interfaces.PicturePath;
import ru.bironix.super_food.dtos.interfaces.price.BasePrice;
import ru.bironix.super_food.dtos.interfaces.price.BindPrice;
import ru.bironix.super_food.dtos.interfaces.price.CreateUpdatePrice;

public abstract class AddonDto {

    @Schema(description = "Добавка. Создание, обновление", name = "AddonDto.CreateUpdate")
    @Data
    @SuperBuilder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class CreateUpdate implements Name, PicturePath, CreateUpdatePrice, Deleted {
        String name;
        String picturePath;
        PriceDto.CreateUpdate price;
        Boolean deleted;
    }


    @Schema(description = "Добавка. Базовая", name = "AddonDto.Base")
    @Data
    @SuperBuilder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Base implements Id, Name, PicturePath, BasePrice, Deleted {
        Integer id;
        String name;
        String picturePath;
        PriceDto.Base price;
        Boolean deleted;
    }

    @Schema(description = "Добавка. Связующая", name = "AddonDto.Bind")
    @Data
    @SuperBuilder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Bind implements Id {
        Integer id;
    }

    @Schema(description = "Добавка. Связующая для заказа", name = "AddonDto.BindForOrder")
    @Data
    @SuperBuilder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class BindForOrder implements Id, BindPrice {
        Integer id;
        PriceDto.Bind price;
    }

}