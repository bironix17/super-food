package ru.bironix.super_food.dtos.dish;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

@Schema(description = "Цена, может быть изменчива")
@Data
@Builder
public class PriceDto {

    @NonNull
    Integer id;

    @NonNull
    Integer price;
}
