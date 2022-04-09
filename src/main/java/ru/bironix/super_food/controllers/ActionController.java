package ru.bironix.super_food.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.bironix.super_food.dtos.action.FullActionDto;
import ru.bironix.super_food.dtos.responses.ActionsResponseDto;
import ru.bironix.super_food.services.ActionService;

@Tag(name = "Акции")
@RestController
@RequestMapping("/api")
public class ActionController {

    @Autowired
    ActionService actionService;

    @Operation(summary = "Получение списка акций", description = "**Пока возвращает захардоженный объект !!!!!**")
    @GetMapping("/actions")
    @ResponseBody
    ActionsResponseDto getActions() {
        return actionService.getActions();
    }

    @Operation(summary = "Получение конкретной акции", description = "**Пока возвращает захардоженный объект !!!!!**")
    @GetMapping("/action/{id}")
    @ResponseBody
    FullActionDto getAction(@PathVariable @Parameter(description = "id") int id) {
        return actionService.getAction(id);
    }
}