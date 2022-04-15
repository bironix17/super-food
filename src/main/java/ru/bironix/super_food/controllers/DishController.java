package ru.bironix.super_food.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.bironix.super_food.converters.DishConverter;
import ru.bironix.super_food.dtos.dish.AbstractDishDto;
import ru.bironix.super_food.dtos.dish.AddonDto;
import ru.bironix.super_food.dtos.dish.CategoryDto;
import ru.bironix.super_food.dtos.dish.FullDishDto;
import ru.bironix.super_food.dtos.responses.DishesInCategoriesDto;
import ru.bironix.super_food.services.DishService;

import java.util.stream.Collectors;

@Tag(name = "Блюдо")
@RestController

public class DishController {

    @Autowired
    DishConverter dishConverter;

    @Autowired
    DishService dishService;

    @Operation(summary = "Получение блюда", description = "**Пока возвращает один захардкоженный объект!!!!!**")
    @GetMapping("/dish/{id}")
    @ResponseBody
    FullDishDto getDish(@PathVariable @Parameter(description = "id блюда") int id) {
        return dishConverter.toFullDishDto(dishService.getFullDish(id));
    }

    @Operation(summary = "Получение общего списка блюд по категориям", description = "**Пока возвращает один захардкоженный объект!!!!!**")
    @GetMapping("/dishes")
    @ResponseBody
    DishesInCategoriesDto getDishes() {

        var categories = dishService.getAllDishes().stream()
                .map(dishConverter::toSmallDishDto)
                .collect(Collectors.groupingBy(AbstractDishDto::getCategory))
                .entrySet().stream()
                .map(i -> new CategoryDto(i.getKey(), i.getValue()))
                .collect(Collectors.toList());

        return dishConverter.toDishesInCategoriesDto(categories);
    }

    @Operation(summary = "Создание добавки")
    @PostMapping("/createAddon")
    @ResponseBody
    AddonDto createAddon(@RequestBody AddonDto addon) {
        var addonEntity = dishService.createAddon(dishConverter.fromDto(addon));
        return dishConverter.toDto(addonEntity);
    }
}