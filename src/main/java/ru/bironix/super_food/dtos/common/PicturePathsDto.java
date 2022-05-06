package ru.bironix.super_food.dtos.common;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Schema(description = "Ссылки на картинки разного формата")
@Data
@NoArgsConstructor
public class PicturePathsDto {

    public PicturePathsDto(String large) {
        this.large = large;
    }

    @Schema(description = "Картинка малого разрешения", nullable = true)
    String small;

    @Schema(description = "Картинка среднего разрешения", nullable = true)
    String medium;

    @Schema(description = "Картинка высокого разрешения")
    @NotBlank
    String large;
}
