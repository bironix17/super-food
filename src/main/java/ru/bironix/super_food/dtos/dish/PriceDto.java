package ru.bironix.super_food.dtos.dish;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Schema(description = "Цена, может быть изменчива")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class PriceDto {

    Integer id;

    @NotNull()
    @Min(value = 0)
    @Builder.Default
    Integer price = 0;
}
