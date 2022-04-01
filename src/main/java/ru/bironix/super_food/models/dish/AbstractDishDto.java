package ru.bironix.super_food.models.dish;

import io.swagger.annotations.ApiModelProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NonNull;
import lombok.experimental.SuperBuilder;
import ru.bironix.super_food.models.PicturePathsDto;

import java.util.List;

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

    @Schema(description = "Состав блюда")
    @NonNull
    String composition;

    @ApiModelProperty
    @Schema(description = "Категория")
    @NonNull
    CategoryType category;

    @Schema(description = "Перечень блюд комбо, не null если category = COMBO", nullable = true)
    List<SmallDishDto> dishes;
}
