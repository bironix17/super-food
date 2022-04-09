package ru.bironix.super_food.dtos.dish;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

@Schema(description = "Добавка к блюду")
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class AddonDto {

    @NonNull
    Integer id;

    @NotBlank
    @NonNull
    String name;

    @NotBlank
    @NonNull
    String picturePath;

    @Valid
    @NonNull
    PriceDto price;
}