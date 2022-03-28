package ru.bironix.super_food.services;

import org.springframework.stereotype.Service;
import ru.bironix.super_food.models.UserDto;

import java.util.List;

@Service
public class UserService { //TODO


    public UserDto getMe() {
        return UserDto.builder()
                .id(0)
                .name("Виктори")
                .email("rnosov@sfedu.ru")
                .password("katia4size")
                .addresses(List.of("Москва, ква ква"))
                .build();
    }
}
