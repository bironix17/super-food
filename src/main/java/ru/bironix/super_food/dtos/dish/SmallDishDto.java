package ru.bironix.super_food.dtos.dish;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Schema(description = "Сжатое блюдо")
@Data
@SuperBuilder
@NoArgsConstructor
public class SmallDishDto extends AbstractDishDto {

    @Schema(description = "базовая порция блюда, расположена по индексу, хранящейся в baseIndexPortion из portions")
    @NotNull
    PortionDto basePortion;
}
