package ru.bironix.super_food.dtos.person;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
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
    @EqualsAndHashCode(callSuper = true)
    @SuperBuilder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Base extends Update implements Id {
        Integer id;
    }


    @Schema(description = "Пользователь. Сущность для указания в заказе", name = "PersonDto.Bind")
    @Data
    @SuperBuilder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Bind implements Id {
        Integer id;
    }
}