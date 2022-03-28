package ru.bironix.super_food.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import ru.bironix.super_food.models.dish.FullDishDto;
import ru.bironix.super_food.models.dish.SmallDishDto;
import ru.bironix.super_food.services.DishService;

import java.util.List;

@Tag(name = "Блюда")
@RestController
public class DishController {

    @Autowired
    DishService dishService;

    @Operation(summary = "Получение блюда", description = "**Пока возвращает один захардкоженный объект!!!!!**")
    @GetMapping("/dish/{id}")
    @ResponseBody
    FullDishDto getDish(@PathVariable @Parameter(description = "id блюда") int id) {
        return dishService.getFullDish(id);
    }

    @Operation(summary = "Получение общего списка блюд ", description = "**Пока возвращает один захардкоженный объект!!!!!**")
    @GetMapping("/dishes")
    @ResponseBody
    List<SmallDishDto> getDishes() {
        return dishService.getDishes();
    }
}