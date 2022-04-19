package ru.bironix.super_food.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.bironix.super_food.converters.DishConverter;
import ru.bironix.super_food.dtos.action.FullActionDto;
import ru.bironix.super_food.dtos.responses.ActionsResponseDto;
import ru.bironix.super_food.services.ActionService;

import static java.util.stream.Collectors.toList;

@Tag(name = "Акции")
@RestController
public class ActionController {

    @Autowired
    ActionService actionService;
    @Autowired
    DishConverter actionConverter;

    @Operation(summary = "Получение списка акций")
    @GetMapping("/actions")
    @ResponseBody
    ActionsResponseDto getActions() {

        var actionsDtos = actionService.getActions().stream()
                .map(actionConverter::toSmallDto)
                .collect(toList());
        return actionConverter.toActionsResponseDto(actionsDtos);
    }

    @Operation(summary = "Получение конкретной акции")
    @GetMapping("/action/{id}")
    @ResponseBody
    FullActionDto getAction(@PathVariable @Parameter(description = "id") int id) {
        return actionConverter.toFullDto(actionService.getAction(id));
    }
}