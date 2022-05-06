package ru.bironix.super_food.dtos.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Сущность авторизации")
public class AuthResponseDto {

    @Schema(description = "id пользователя")
    Integer personId;

    @Schema(description = "Токен авторизации. Для доступа к ресурсам системы данный токен необходимо указывать в " +
            "headers под именем \"Authorization\"")
    String token;
}
