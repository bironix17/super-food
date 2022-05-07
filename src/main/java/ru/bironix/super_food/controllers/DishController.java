package ru.bironix.super_food.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.bironix.super_food.converters.Converter;
import ru.bironix.super_food.dtos.dish.AddonDto;
import ru.bironix.super_food.dtos.dish.CategoryDto;
import ru.bironix.super_food.dtos.dish.FullDishDto;
import ru.bironix.super_food.dtos.dish.SmallDishDto;
import ru.bironix.super_food.dtos.response.ApiActionResponseDto;
import ru.bironix.super_food.services.DishService;

import javax.validation.constraints.Min;
import java.util.HashSet;
import java.util.List;

@Tag(name = "Блюдо")
@RestController
@Validated
@SecurityRequirement(name = "bearerAuth")
public class DishController {

    final Converter con;
    final DishService service;

    @Autowired
    public DishController(Converter con, DishService service) {
        this.con = con;
        this.service = service;
    }

    @Operation(summary = "Создание блюда")
    @PostMapping("/dishes")
    FullDishDto createDish(@RequestBody
                           @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Блюдо")
                           FullDishDto dishDto) {
        return null;
    }

    @Operation(summary = "Получение блюда")
    @GetMapping("/dishes/{id}")
    FullDishDto getDish(@PathVariable
                        @Parameter(description = "id блюда")
                        @Min(0) int id) {
        return con.toFullDto(service.getFullDish(id));
    }

    @Operation(summary = "Изменение блюда")
    @PutMapping("/dishes/{id}")
    FullDishDto updateDish(@RequestBody
                           @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Блюдо")
                           FullDishDto dishDto,
                           @PathVariable
                           @Parameter(description = "id блюда")
                           @Min(0) int id) {
        dishDto.setId(id);
        return null;
    }

    @Operation(summary = "Удаление блюда")
    @DeleteMapping("/dishes/{id}")
    ApiActionResponseDto deleteDish(@PathVariable
                                    @Parameter(description = "id блюда")
                                    @Min(0) int id) {
        return null;
    }


    @Operation(summary = "Создание добавки")
    @PostMapping("/addons")
    AddonDto createAddon(@RequestBody
                         @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Добавка")
                         AddonDto addonDto) {
        return null;
    }

    @Operation(summary = "Получение блюда")
    @GetMapping("/addons/{id}")
    AddonDto getAddon(@PathVariable
                      @Parameter(description = "id добавки")
                      @Min(0) int id) {
        return null;
    }


    @Operation(summary = "Изменение добавки")
    @PutMapping("/addons/{id}")
    AddonDto updateAddon(@RequestBody
                         @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Добавка")
                         AddonDto addonDto,
                         @PathVariable
                         @Parameter(description = "id добавки")
                         @Min(0) int id) {
        addonDto.setId(id);
        return null;
    }


    @Operation(summary = "Удаление добавки")
    @DeleteMapping("/addons/{id}")
    ApiActionResponseDto deleteAddon(@PathVariable
                                     @Parameter(description = "id добавки")
                                     @Min(0) int id) {
        return null;
    }


    @Operation(summary = "Получение общего списка блюд по категориям")
    @GetMapping("/dishes")
    List<CategoryDto> getDishes() {
        return con.toCategoriesDto(service.getAllDishes());
    }

    @Operation(summary = "Получение запрошеного списка блюд")
    @GetMapping("/specificDishes")
    List<SmallDishDto> getSpecificDishes(@RequestParam("ids")
                                         @Parameter(description = "Список id блюд")
                                         List<@Min(0) Integer> ids) {
        return con.toDishesDto(service.getDishes(new HashSet<>(ids)));
    }

}