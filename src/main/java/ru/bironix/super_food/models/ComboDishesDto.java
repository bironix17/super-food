package ru.bironix.super_food.models;

import io.swagger.annotations.ApiModelProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;
import ru.bironix.super_food.models.dish.FullDishDto;
import ru.bironix.super_food.models.dish.SmallDishDto;

import java.util.List;

@Schema(description = "Комбо блюд")
@Data
@Builder
public class ComboDishesDto {

    @NonNull
    Integer id;

    @NonNull
    String name;

    @Schema(description = "Цена комбо, не null если comboTypeDto == COMBO_DISCOUNT")
    Integer totalPrice;

    @Schema(description = "Перечень блюд конкретного комбо")
    @NonNull
    List<SmallDishDto> dishes;

    @ApiModelProperty
    @Schema(description = "Тип комбо")
    @NonNull
    ComboTypeDto comboType;

    @Schema(description = "Подарок, не null если comboTypeDto == COMBO_GIFT")
    SmallDishDto giftDish;
}
