package ru.bironix.super_food.dtos.request.createOrder;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Schema(description = "Порция блюда. Версия для указания в заказе")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PortionRequestDto {
    @NotNull
    Integer id;

    @NotNull
    PriceRequestDto price;
}
