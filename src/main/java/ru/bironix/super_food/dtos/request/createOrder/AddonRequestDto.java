package ru.bironix.super_food.dtos.request.createOrder;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.validation.constraints.NotNull;

@Schema(description = "Добавка к блюду. Версия для указания  в заказе")
@Data
@NoArgsConstructor
@ToString
public class AddonRequestDto {

    @NotNull
    Integer id;
}