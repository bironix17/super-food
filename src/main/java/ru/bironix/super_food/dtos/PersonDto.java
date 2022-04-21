package ru.bironix.super_food.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.ArrayList;
import java.util.List;

@Schema(description = "Пользователь, точно еще поменяется структура")
@Data
@Builder
public class PersonDto {

    Integer id;

    @NotBlank
    String email;

    @NotBlank
    String password;

    @NotBlank
    String name;

    @Valid
//    @NonNull
    @Builder.Default
    List<AddressDto> addresses = new ArrayList<>();
}