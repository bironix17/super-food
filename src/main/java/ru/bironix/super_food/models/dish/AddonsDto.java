package ru.bironix.super_food.models.dish;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NonNull;

@Schema(description = "Добавка к блюду")
@Data
public class AddonsDto {

    @NonNull
    Integer id;

    @NonNull
    String name;

    @NonNull
    String picturePath;

    @NonNull
    Integer price;
}