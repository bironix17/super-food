package ru.bironix.super_food.models;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;


@Schema(description = "Сущность сжатого блюда")
@Data
@AllArgsConstructor
public class SmallDishDto {

    @Schema(description = "Ссылки на картинки")
    @NonNull
    PicturePathsDto picturePaths;

    @NonNull
    String name;

    @NonNull
    int price;

    @NonNull
    String size;
}
