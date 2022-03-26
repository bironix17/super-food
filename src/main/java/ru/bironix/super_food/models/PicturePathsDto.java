package ru.bironix.super_food.models;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Schema(description = "Сущность ссылок на картинки разного формата")
@RequiredArgsConstructor
@Data
public class PicturePathsDto {

    @Schema(description = "Картинка малого разрешения", nullable = true)
    String small;

    @Schema(description = "Картинка среднего разрешения", nullable = true)
    String medium;

    @Schema(description = "Картинка высокого разрешения")
    @NonNull
    String large;
}
