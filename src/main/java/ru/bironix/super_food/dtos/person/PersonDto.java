package ru.bironix.super_food.dtos.person;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;

@Schema(description = "Пользователь, пароль еще поменяется")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PersonDto {

    Integer id;

    @NotBlank
    @Email
    String email;

    @NotBlank
    String password;

    String name;

    @Valid
    @Builder.Default
    List<AddressDto> addresses = new ArrayList<>();
}