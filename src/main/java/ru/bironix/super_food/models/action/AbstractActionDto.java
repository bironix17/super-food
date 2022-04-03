package ru.bironix.super_food.models.action;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NonNull;
import lombok.experimental.SuperBuilder;
import ru.bironix.super_food.models.PicturePathsDto;

@Data
@SuperBuilder
public class AbstractActionDto {

    @NonNull
    Integer id;

    @NonNull
    String name;

    @Schema(description = "Картинки")
    @NonNull
    PicturePathsDto picturePaths;
}
