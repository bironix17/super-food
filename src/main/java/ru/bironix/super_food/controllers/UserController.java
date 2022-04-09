package ru.bironix.super_food.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import ru.bironix.super_food.dtos.UserDto;
import ru.bironix.super_food.services.UserService;

@Tag(name = "Пользователь")
@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired
    UserService userService;


    @Operation(summary = "Получить инфу о себе", description = "**Пока возвращает один захардкоженный объект!!!!!**")
    @GetMapping("/getMe")
    @ResponseBody
    UserDto getMe() {
        return userService.getMe();
    }
}
