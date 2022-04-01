package ru.bironix.super_food.models.action;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.SuperBuilder;
import ru.bironix.super_food.models.dish.SmallDishDto;

import java.util.List;

@Schema(description = "акция")
@Data
@SuperBuilder
public class FullActionDto extends AbstractActionDto {

//    @Schema(description = "комбы, не null если actionType ==  COMBO_DISCOUNT, COMBO_GIFT")
//    List<ComboDishesDto> combos;
//
//    @Schema(description = "комбы, не null если actionType == DISH_DISCOUNT")
//    List<SmallDishDto> discountDishes;

}
