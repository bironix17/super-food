package ru.bironix.super_food.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import ru.bironix.super_food.converters.Converter;
import ru.bironix.super_food.dtos.action.FullActionDto;
import ru.bironix.super_food.dtos.responses.ActionsResponseDto;
import ru.bironix.super_food.services.ActionService;

import javax.validation.constraints.Min;

@Tag(name = "Акции")
@RestController
@Validated
public class ActionController {

    @Autowired
    public ActionController(ActionService service, Converter converter) {
        this.service = service;
        this.converter = converter;
    }

    final ActionService service;
    final Converter converter;

    @Operation(summary = "Получение списка акций")
    @GetMapping("/actions")
    @ResponseBody
    ActionsResponseDto getActions() {
        return converter.toActionsResponseDto(service.getActions());
    }

    @Operation(summary = "Получение конкретной акции")
    @GetMapping("/action/{id}")
    @ResponseBody
    FullActionDto getAction(@PathVariable @Parameter(description = "id") @Min(0) int id) {
        return converter.toFullDto(service.getAction(id));
    }
}