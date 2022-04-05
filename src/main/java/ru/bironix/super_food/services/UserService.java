package ru.bironix.super_food.services;

import org.springframework.stereotype.Service;
import ru.bironix.super_food.Utils;
import ru.bironix.super_food.dtos.UserDto;

@Service
public class UserService { //TODO


    public UserDto getMe() {
        return Utils.getMockUser();
    }
}
