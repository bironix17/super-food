package ru.bironix.super_food.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.bironix.super_food.converters.Converter;
import ru.bironix.super_food.dtos.dish.CategoryDto;
import ru.bironix.super_food.dtos.dish.FullDishDto;
import ru.bironix.super_food.dtos.dish.SmallDishDto;
import ru.bironix.super_food.services.DishService;

import javax.validation.constraints.Min;
import java.util.HashSet;
import java.util.List;

@Tag(name = "Блюдо")
@RestController
@Validated
@SecurityRequirement(name = "bearerAuth")
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
//    @PreAuthorize("hasAnyRole('ALL')")
    List<CategoryDto> getDishes() {
        return con.toCategoryDto(service.getAllDishes());
    }

    @Operation(summary = "Получение запрошеного списка блюд")
    @GetMapping("/specificDishes")
    @ResponseBody
    List<SmallDishDto> getSpecificDishes(@RequestParam("ids") @Parameter(description = "Список id блюд") List<@Min(0) Integer> ids) {
        return con.toDishesDto(service.getDishes(new HashSet<>(ids)));
    }

}