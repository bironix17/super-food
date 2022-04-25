package ru.bironix.super_food.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.bironix.super_food.converters.Converter;
import ru.bironix.super_food.dtos.dish.FullDishDto;
import ru.bironix.super_food.dtos.dish.SmallDishDto;
import ru.bironix.super_food.dtos.responses.DishesInCategoriesResponseDto;
import ru.bironix.super_food.dtos.responses.DishesResponseDto;
import ru.bironix.super_food.services.DishService;

import javax.validation.constraints.Min;
import java.util.List;

@Tag(name = "Блюдо")
@RestController
@Validated
public class DishController {

    @Autowired
    public DishController(Converter con, DishService service) {
        this.con = con;
        this.service = service;
    }

    final Converter con;
    final DishService service;

    @Operation(summary = "Получение блюда")
    @GetMapping("/dish/{id}")
    @ResponseBody
    FullDishDto getDish(@PathVariable @Parameter(description = "id блюда") @Min(0) int id) {
        return con.toFullDto(service.getFullDish(id));
    }

    @Operation(summary = "Получение общего списка блюд по категориям")
    @GetMapping("/dishes")
    @ResponseBody
    DishesInCategoriesResponseDto getDishes() {
        return con.toDishesInCategoriesResponseDto(service.getAllDishes());
    }

    @Operation(summary = "Получение запрошеного списка блюд" )
    @GetMapping("/specificDishes")
    @ResponseBody
    DishesResponseDto getSpecificDishes(@RequestParam("ids[]") @Parameter(description = "Список id блюд") List<@Min(0) Integer> ids) {
        return con.toDishesResponseDto(service.getDishes(ids));
    }

    @Operation(summary = "Получение запрошеного списка блюд. Возвращает СПИСОК!!!!!!!!!!!!!!" )
    @GetMapping("/specificDishesTestFront")
    @ResponseBody
    List<SmallDishDto> getSpecificDishesTestFront(@RequestParam("ids[]") @Parameter(description = "Список id блюд") List<@Min(0) Integer> ids) {
        return con.toDishes(service.getDishes(ids));
    }

    @Operation(summary = "Получение общего списка добавок")
    @GetMapping("/addons")
    @ResponseBody
    DishesInCategoriesResponseDto getAddons() {
        return con.toDishesInCategoriesResponseDto(service.getAllDishes());
    }

}