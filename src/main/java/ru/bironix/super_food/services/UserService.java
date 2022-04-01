package ru.bironix.super_food.services;

import org.springframework.stereotype.Service;
import ru.bironix.super_food.Utils;
import ru.bironix.super_food.models.UserDto;

import java.util.List;

@Service
public class UserService { //TODO


    public UserDto getMe() {
        return Utils.getMockUser();
    }
}
