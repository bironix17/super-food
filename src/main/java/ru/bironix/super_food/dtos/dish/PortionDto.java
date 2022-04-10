package ru.bironix.super_food.dtos.dish;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Schema(description = "Порция блюда")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PortionDto {

    Integer id;

    @NotBlank
    @Schema(description = "размер порции, null если category = COMBO", example = "100 грамм", nullable = true)
    String size;

    @Valid
    @NotNull
    PriceDto priceNow;

    @Valid
    @Schema(description = "если есть скидка, то не null", nullable = true)
    PriceDto oldPrice;
}
