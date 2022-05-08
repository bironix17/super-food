package ru.bironix.super_food.dtos.order;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import ru.bironix.super_food.dtos.dish.FullDishDto;
import ru.bironix.super_food.dtos.person.AddressDto;
import ru.bironix.super_food.dtos.person.PersonDto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Schema(description = "Заказ")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class OrderDto implements Serializable {
    private Integer id;

    @Builder.Default
    private Date created = new Date();

    @JsonFormat(pattern = "HH:mm")
    private Date deliveryTime;

    @Builder.Default
    private OrderStatusDto status = OrderStatusDto.EXPECTS;

    @Builder.Default
    private WayToGetDto wayToGet = WayToGetDto.PICKUP;

    @NotNull
    private int totalPrice;

    @NotEmpty
    private List<FullDishDto> dishes;

    @NotEmpty
    private PersonDto client;

    @NotEmpty
    private AddressDto address;
}
