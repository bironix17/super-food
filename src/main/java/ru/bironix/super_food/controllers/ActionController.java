package ru.bironix.super_food.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import ru.bironix.super_food.converters.Converter;
import ru.bironix.super_food.dtos.action.FullActionDto;
import ru.bironix.super_food.dtos.action.SmallActionDto;
import ru.bironix.super_food.services.ActionService;

import javax.validation.constraints.Min;
import java.util.List;

@Tag(name = "Акции")
@RestController
@Validated
@SecurityRequirement(name = "bearerAuth")
public class ActionController {

    final ActionService service;
    final Converter converter;

    @Autowired
    public ActionController(ActionService service, Converter converter) {
        this.service = service;
        this.converter = converter;
    }

    @Operation(summary = "Получение списка акций")
    @GetMapping("/actions")
    List<SmallActionDto> getActions() {
        return converter.toActionsDto(service.getActions());
    }

    @Operation(summary = "Получение конкретной акции")
    @GetMapping("/action/{id}")
    FullActionDto getAction(@PathVariable @Parameter(description = "id") @Min(0) int id) {
        return converter.toFullDto(service.getAction(id));
    }
}