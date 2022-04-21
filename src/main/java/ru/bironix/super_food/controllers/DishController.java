package ru.bironix.super_food.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.bironix.super_food.converters.Converter;
import ru.bironix.super_food.dtos.dish.AddonDto;
import ru.bironix.super_food.dtos.dish.FullDishDto;
import ru.bironix.super_food.dtos.responses.DishesResponseDto;
import ru.bironix.super_food.services.DishService;

@Tag(name = "Блюдо")
@RestController
public class DishController {

    @Autowired
    public DishController(Converter converter, DishService service) {
        this.converter = converter;
        this.service = service;
    }

    final Converter converter;
    final DishService service;

    @Operation(summary = "Получение блюда")
    @GetMapping("/dish/{id}")
    @ResponseBody
    FullDishDto getDish(@PathVariable @Parameter(description = "id блюда") int id) {
        return converter.toFullDto(service.getFullDish(id));
    }

    @Operation(summary = "Получение общего списка блюд по категориям")
    @GetMapping("/dishes")
    @ResponseBody
    DishesResponseDto getDishes() {
        return converter.toDishesResponseDto(service.getAllDishes());
    }


    @Operation(summary = "Получение общего списка добавок")
    @GetMapping("/addons")
    @ResponseBody
    DishesResponseDto getAddons() {
        return converter.toDishesResponseDto(service.getAllDishes());
    }

}