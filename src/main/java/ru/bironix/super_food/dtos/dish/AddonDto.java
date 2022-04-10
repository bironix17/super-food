package ru.bironix.super_food.dtos.dish;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

@Schema(description = "Добавка к блюду")
@Data
@NoArgsConstructor
@ToString
public class AddonDto {

    public AddonDto(String name, PriceDto price) {
        this.name = name;
        this.price = price;
    }

    //    @NonNull
    Integer id;

    @NotBlank
//    @NonNull
    String name;

    @NotBlank
//    @NonNull
    String picturePath;

    @Valid
//    @NonNull
    PriceDto price;
}