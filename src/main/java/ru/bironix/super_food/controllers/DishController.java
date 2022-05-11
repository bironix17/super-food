package ru.bironix.super_food.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.bironix.super_food.converters.Converter;
import ru.bironix.super_food.dtos.dish.AddonDto;
import ru.bironix.super_food.dtos.dish.CategoryDto;
import ru.bironix.super_food.dtos.dish.DishDto;
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
    @PostMapping("/admin/dishes")
    DishDto.Base.Full createDish(@RequestBody
                                 @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Блюдо")
                                 DishDto.CreateUpdate dishDto) {
        var dish = service.createDish(con.fromDto(dishDto));
        return con.toFullDto(dish);
    }

    @Operation(summary = "Получение блюда")
    @GetMapping({"client/dishes/{id}", "/admin/dishes/{id}"})
    DishDto.Base.Full getDish(@PathVariable
                              @Parameter(description = "id блюда")
                              @Min(0) int id) {
        return con.toFullDto(service.getDish(id));
    }

    @Operation(summary = "Изменение блюда")
    @PutMapping("/admin/dishes/{id}")
    DishDto.Base.Full updateDish(@RequestBody
                                 @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Блюдо")
                                 DishDto.CreateUpdate dishDto,
                                 @PathVariable
                                 @Parameter(description = "id блюда")
                                 @Min(0) int id) {
        var dish = con.fromDto(dishDto);
        dish.setId(id);
        return con.toFullDto(service.updateDish(dish));
    }

    @Operation(summary = "Удаление блюда")
    @DeleteMapping("/admin/dishes/{id}")
    ApiActionResponseDto deleteDish(@PathVariable
                                    @Parameter(description = "id блюда")
                                    @Min(0) int id) {
        service.deleteDish(id);
        return new ApiActionResponseDto(true);
    }


    @Operation(summary = "Создание добавки")
    @PostMapping("/admin/addons")
    AddonDto.Base createAddon(@RequestBody
                              @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Добавка")
                              AddonDto.CreateUpdate addonDto) {
        var addon = service.createAddon(con.fromDto(addonDto));
        return con.toDto(addon);
    }

    @Operation(summary = "Получение добавки")
    @GetMapping({"/client/addons/{id}", "/admin/addons/{id}"})
    AddonDto.Base getAddon(@PathVariable
                           @Parameter(description = "id добавки")
                           @Min(0) int id) {
        return con.toDto(service.getAddon(id));
    }

    @Operation(summary = "Изменение добавки")
    @PutMapping("/admin/addons/{id}")
    AddonDto.Base updateAddon(@RequestBody
                              @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Добавка")
                              AddonDto.CreateUpdate addonDto,
                              @PathVariable
                              @Parameter(description = "id добавки")
                              @Min(0) int id) {

        var addon = con.fromDto(addonDto);
        addon.setId(id);
        return con.toDto(service.updateAddon(addon));
    }


    @Operation(summary = "Удаление добавки")
    @DeleteMapping("/admin/addons/{id}")
    ApiActionResponseDto deleteAddon(@PathVariable
                                     @Parameter(description = "id добавки")
                                     @Min(0) int id) {
        service.deleteAddon(id);
        return new ApiActionResponseDto(true);
    }


    @Operation(summary = "Получение всего списка блюд по категориям")
    @GetMapping({"/client/dishes/categories", "/admin/dishes/categories"})
    List<CategoryDto> getDishesInCategories() {
        return con.toCategoriesDto(service.getDishes());
    }

    @Operation(summary = "Получение актуального списка блюд по категориям. (Без удалённых)")
    @GetMapping({"/client/dishes/actual/categories", "/admin/dishes/actual/categories"})
    List<CategoryDto> getActualDishesInCategories() {
        return con.toCategoriesDto(service.getActualDishes());
    }

    @Operation(summary = "Получение запрошенного списка блюд")
    @GetMapping({"/client/specificDishes", "/admin/specificDishes"})
    List<DishDto.Base.Small> getSpecificDishes(@RequestParam("ids")
                                               @Parameter(description = "Список id блюд")
                                               List<@Min(0) Integer> ids) {
        return con.toDishesDto(service.getDishes(new HashSet<>(ids)));
    }

}