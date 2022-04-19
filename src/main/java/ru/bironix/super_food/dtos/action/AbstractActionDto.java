package ru.bironix.super_food.dtos.action;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import ru.bironix.super_food.dtos.PicturePathsDto;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class AbstractActionDto {

    Integer id;

    @NotBlank
    String name;

    @Valid
    @Schema(description = "Картинки")
    PicturePathsDto picturePaths;
}
