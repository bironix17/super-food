package ru.bironix.super_food.dtos.dish;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import ru.bironix.super_food.dtos.interfaces.Deleted;
import ru.bironix.super_food.dtos.interfaces.Id;
import ru.bironix.super_food.dtos.interfaces.Size;
import ru.bironix.super_food.dtos.interfaces.price.*;

public abstract class PortionDto {

    @Schema(description = "Порция. Создание, обновление", name = "PortionDto.CreateUpdate")
    @Data
    @SuperBuilder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class CreateUpdate implements Size, CreateUpdatePriceNow, CreateUpdateOldPrice {
        String size;
        PriceDto.CreateUpdate priceNow;
        PriceDto.CreateUpdate oldPrice;
    }


    @Schema(description = "Порция. Базовая", name = "PortionDto.Base")
    @Data
    @SuperBuilder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Base implements Id, Size, PriceNow, OldPrice, Deleted {
        Integer id;
        String size;
        PriceDto.Base priceNow;
        PriceDto.Base oldPrice;
        Boolean deleted;
    }


    @Schema(description = "Порция. Связывающая", name = "PortionDto.Bind")
    @Data
    @SuperBuilder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Bind implements Id, BindPrice {
        Integer id;
        PriceDto.Bind price;
    }

    @Schema(description = "Порция. Для указания в акции", name = "PortionDto.CreateUpdateForAction")
    @Data
    @SuperBuilder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class CreateUpdateForAction implements Id, CreateUpdatePrice {
        Integer id;
        PriceDto.CreateUpdate price;
    }
}
