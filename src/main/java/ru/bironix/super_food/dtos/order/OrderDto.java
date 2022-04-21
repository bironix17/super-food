package ru.bironix.super_food.dtos.order;

import io.swagger.annotations.ApiModelProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;
import ru.bironix.super_food.dtos.PersonDto;
import ru.bironix.super_food.dtos.dish.SmallDishDto;

import java.util.Date;
import java.util.List;

@Schema(description = "Заказ")
@Data
@Builder
public class OrderDto {


    Integer id;


    PersonDto user;

    //TODO
    @Schema(description = "**Еще не знаю правильный формат**")

    Date dateTime;


    Integer totalPrice;

    @ApiModelProperty
    @Builder.Default

    OrderStatus orderStatus = OrderStatus.ACCEPTED;
//
//    @Schema(description = "Заказанные комбы")
//    List<ComboDishesDto> comboDishes;

    @Schema(description = "Заказанные блюда")
    List<SmallDishDto> dishes;
}
