package ru.bironix.super_food.dtos.order;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import ru.bironix.super_food.dtos.AddressDto;
import ru.bironix.super_food.dtos.PersonDto;
import ru.bironix.super_food.dtos.dish.FullDishDto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;
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
    private LocalDateTime created = LocalDateTime.now();

    @Builder.Default
    private LocalDateTime targetProduction = LocalDateTime.now();

    @Builder.Default
    private StatusDto status = StatusDto.EXPECTS;

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
