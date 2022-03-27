package ru.bironix.super_food.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import ru.bironix.super_food.models.FullDishDto;
import ru.bironix.super_food.models.PicturePathsDto;
import ru.bironix.super_food.services.DishService;

@Tag(name = "Блюдо")
@RestController
public class DishController {

    @Autowired
    DishService dishService;

    @Operation(summary = "Получение блюда", description = "Пока возвращает один захардкоженный объект")
    @GetMapping("/dish/{id}")
    @ResponseBody
    FullDishDto getDish(@PathVariable @Parameter(description = "id блюда") int id) {
        return dishService.getFullDish(id);
    }
}