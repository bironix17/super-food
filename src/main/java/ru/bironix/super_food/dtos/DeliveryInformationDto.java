package ru.bironix.super_food.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Schema(description = "Информация о доставке")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DeliveryInformationDto {
    @NotNull @Min(0)
    Integer deliveryPrice;
}
