package ru.bironix.super_food.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.bironix.super_food.dtos.interfaces.Password;
import ru.bironix.super_food.dtos.interfaces.PhoneNumber;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthRequestDto implements PhoneNumber, Password {

    String phoneNumber;
    String password;
}
