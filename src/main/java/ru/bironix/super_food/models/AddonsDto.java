package ru.bironix.super_food.models;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.NonNull;

@Schema(description = "Сущность добавки")
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