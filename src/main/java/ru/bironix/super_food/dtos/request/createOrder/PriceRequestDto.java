package ru.bironix.super_food.dtos.request.createOrder;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import javax.validation.constraints.NotNull;

@Schema(description = "Цена. Версия для указания  в заказе")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class PriceRequestDto {
    @NotNull
    Integer id;
}
