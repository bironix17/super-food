package ru.bironix.super_food.dtos.dish;

import io.swagger.annotations.ApiModelProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import ru.bironix.super_food.dtos.common.PicturePathsDto;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@SuperBuilder
@NoArgsConstructor
public abstract class AbstractDishDto {

    Integer id;

    @Schema(description = "Ссылки на картинки")
    @Valid
    @NotNull
    PicturePathsDto picturePaths;

    @Schema(description = "название")
    @NotBlank
    String name;

    @Schema(description = "Состав блюда")
    @NotBlank
    String composition;

    @ApiModelProperty
    @Schema(description = "Категория")
    @NotNull
    CategoryType category;

    @Schema(description = "Флаг удаления блюда")
    @Builder.Default
    Boolean deleted = false;

    @Schema(description = "базовая порция блюда")
    PortionDto basePortion;

}
