package ru.bironix.super_food.dtos.dish;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.experimental.SuperBuilder;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Schema(description = "Полное блюдо")
@Data
@SuperBuilder
@NoArgsConstructor
public class FullDishDto extends AbstractDishDto {

    @NotBlank
    @Schema(description = "Описание")
    @NonNull
    String description;

    @NotBlank
    @Schema(description = "аллергены в блюде", nullable = true)
    String allergens;

    @Schema(description = "Индекс основной порции", defaultValue = "0")
    @Builder.Default
    Integer baseIndexPortion = 0;

    @Valid
    @Schema(description = "набор порций блюда")
    @NonNull
    List<PortionDto> portions;

    @Valid
    @Schema(description = "Возможные добавки", nullable = true)
    List<AddonDto> addons;

    @Valid
    @Schema(description = "Перечень блюд комбо, не null если category = COMBO", nullable = true)
    List<SmallDishDto> dishes;


    @Schema(description = "Флаг показа этого блюда в списке всех блюд")
    @Builder.Default
    Boolean hidden = false;
}