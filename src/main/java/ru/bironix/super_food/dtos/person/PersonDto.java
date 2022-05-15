package ru.bironix.super_food.dtos.person;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.SuperBuilder;
import ru.bironix.super_food.dtos.interfaces.*;

import java.util.List;


public abstract class PersonDto {

    @Schema(description = "Пользователь. Создание", name = "PersonDto.Create")
    @Data
    @SuperBuilder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Create implements Email, Password {
        String email;
        String password;
    }

    @Schema(description = "Пользователь. Обновление", name = "PersonDto.Update")
    @Data
    @EqualsAndHashCode(callSuper = true)
    @SuperBuilder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Update extends Create implements Name, Addresses, PhoneNumber {
        String name;
        List<AddressDto> addresses;
        String phoneNumber;
    }


    @Schema(description = "Пользователь. Базовая", name = "PersonDto.Base")
    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Base implements Id, Email, Name, Addresses, PhoneNumber {
        Integer id;
        String email;
        String name;
        List<AddressDto> addresses;
        String phoneNumber;
    }


    @Schema(description = "Пользователь. Связующая", name = "PersonDto.Bind")
    @Data
    @SuperBuilder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Bind implements Id {
        Integer id;
    }
}