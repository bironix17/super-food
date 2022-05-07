package ru.bironix.super_food.dtos.request.createOrder;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import ru.bironix.super_food.dtos.order.WayToGetDto;
import ru.bironix.super_food.dtos.person.PersonDto;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

@Schema(description = "Заказ. Версия для его создания со стороны фронта")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class OrderRequestDto {

    @Builder.Default
    @JsonIgnore
    private Date created = new Date();

    @NotNull
    @Schema(description = "Целевое время вручения клиенту заказа. Пример 10:20", example = "10:20")
    @JsonFormat(pattern = "HH:mm")
    private Date deliveryTime;

    @NotNull
    private int totalPrice;

    @Valid
    @NotEmpty
    private List<DishRequestDto> dishes;

    @Valid
    @Schema(description = "Адрес доставки. Должен быть указан если wayToGet == DELIVERY", nullable = true)
    private AddressRequestDto address;

    @JsonIgnore
    private PersonDto client;

    @Builder.Default
    @NotNull
    @Schema(description = "Способ получения")
    private WayToGetDto wayToGet = WayToGetDto.PICKUP;
}