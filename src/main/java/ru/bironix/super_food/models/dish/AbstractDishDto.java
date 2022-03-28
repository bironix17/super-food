package ru.bironix.super_food.models.dish;

import io.swagger.annotations.ApiModelProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NonNull;
import lombok.experimental.SuperBuilder;
import ru.bironix.super_food.models.PicturePathsDto;

@Data
@SuperBuilder
public abstract class AbstractDishDto {

    @NonNull
    Integer id;

    @Schema(description = "Ссылки на картинки")
    @NonNull
    PicturePathsDto picturePaths;

    @Schema(description = "название")
    @NonNull
    String name;

    @Schema(description = "Описание")
    @NonNull
    String description;

    @ApiModelProperty
    @Schema(description = "Категория")
    @NonNull
    CategoryDto category;
}
