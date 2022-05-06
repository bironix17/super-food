package ru.bironix.super_food.dtos.dish;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

@Schema(description = "Добавка к блюду")
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class AddonDto {

    public AddonDto(String name, PriceDto price) {
        this.name = name;
        this.price = price;
    }

    Integer id;

    @NotBlank
    String name;

    @NotBlank
    String picturePath;

    @Valid
    PriceDto price;
}