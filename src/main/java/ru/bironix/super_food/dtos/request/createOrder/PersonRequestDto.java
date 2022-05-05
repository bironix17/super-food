package ru.bironix.super_food.dtos.request.createOrder;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Schema(description = "Пользователь, пароль еще поменяется")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PersonRequestDto {

    @NotNull
    Integer id;

}