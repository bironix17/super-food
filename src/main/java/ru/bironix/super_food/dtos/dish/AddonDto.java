package ru.bironix.super_food.dtos.dish;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NonNull;

@Schema(description = "Добавка к блюду")
@Data
public class AddonDto {

    @NonNull
    Integer id;

    @NonNull
    String name;

    @NonNull
    String picturePath;

    @NonNull
    PriceDto priceNow;
}