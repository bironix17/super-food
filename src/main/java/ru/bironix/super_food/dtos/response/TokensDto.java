package ru.bironix.super_food.dtos.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Сущность обновления токенов")
public class TokensDto {

    @Schema(description = "Токен авторизации. Для доступа к ресурсам системы данный токен необходимо указывать в " +
            "headers под именем \"Authorization\"")
    String accessToken;

    @Schema(description = "Токен обновления токена авторизации.")
    String refreshToken;
}
