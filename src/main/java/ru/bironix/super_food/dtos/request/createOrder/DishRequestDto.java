package ru.bironix.super_food.dtos.request.createOrder;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.List;

@Schema(description = "Блюдо. Версия для указания  в заказе")
@Data
@SuperBuilder
@NoArgsConstructor
public class DishRequestDto {

    @NotNull
    Integer id;

    @NotNull
    @Schema(description = "Количество блюда")
    @Min(1)
    Integer count;

    @Valid
    @NotNull
    @Schema(description = "Порция блюда")
    PortionRequestDto portion;

    @Valid
    @Schema(description = "Добавки", nullable = true)
    List<AddonRequestDto> addons;
}
