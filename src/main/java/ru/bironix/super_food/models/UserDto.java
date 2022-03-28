package ru.bironix.super_food.models;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

import java.util.List;

@Schema(description = "Пользователь, точно еще поменяется структура")
@Data
@Builder
public class UserDto {

    @NonNull
    Integer id;

    @NonNull
    String email;

    //TODO сделать через хэш
    @NonNull
    String password;

    @NonNull
    String name;

    List<String> addresses;
}