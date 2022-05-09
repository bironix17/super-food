package ru.bironix.super_food.dtos.dish;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.SuperBuilder;
import ru.bironix.super_food.dtos.interfaces.Id;
import ru.bironix.super_food.dtos.interfaces.Price;

public abstract class PriceDto {

    @Schema(description = "Цена. Создание, обновление", name = "PriceDto.CreateUpdate")
    @Data
    @SuperBuilder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class CreateUpdate implements Price {
        @Builder.Default
        Integer price = 0;
    }

    @Schema(description = "Цена. Базовая", name = "PriceDto.Base")
    @Data
    @EqualsAndHashCode(callSuper = true)
    @SuperBuilder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Base extends CreateUpdate implements Id {
        Integer id;
    }


    @Schema(description = "Цена. Сущность для указания в заказе", name = "PriceDto.Bind")
    @Data
    @SuperBuilder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Bind implements Id {
        Integer id;
    }
}
