package ru.bironix.super_food.dtos.request.createOrder;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import ru.bironix.super_food.dtos.order.WayToGetDto;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
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
    private LocalDateTime created = LocalDateTime.now();

    @NotNull
    @Schema(description = "Целевое время вручения заказчику заказа. Пример 2022-05-05T13:52:41.109735", example = "2022-05-05T13:52:41.109735")
    private LocalDateTime targetProduction;

    @NotNull
    private int totalPrice;

    @Valid
    @NotEmpty
    private List<DishRequestDto> dishes;

    @Valid
    @Schema(description = "Адрес доставки. Должен быть указан если wayToGet == DELIVERY", nullable = true)
    private AddressRequestDto address;

    @Valid
    @NotNull
    private PersonRequestDto client;

    @Builder.Default
    @NotNull
    @Schema(description = "Способ получения")
    private WayToGetDto wayToGet = WayToGetDto.PICKUP;
}