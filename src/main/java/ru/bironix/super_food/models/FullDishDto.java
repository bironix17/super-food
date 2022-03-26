package ru.bironix.super_food.models;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.NonNull;

@Schema(description = "Блюдо")
public class FullDishDto extends SmallDishDto{

    public FullDishDto(@NonNull PicturePathsDto picturePaths, @NonNull String name, @NonNull int price, @NonNull String size) {
        super(picturePaths, name, price, size);
    }
}
