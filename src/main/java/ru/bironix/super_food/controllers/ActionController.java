package ru.bironix.super_food.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.bironix.super_food.converters.Converter;
import ru.bironix.super_food.db.models.action.Action;
import ru.bironix.super_food.dtos.action.ActionDto;
import ru.bironix.super_food.dtos.response.ApiActionResponseDto;
import ru.bironix.super_food.services.ActionService;

import javax.validation.constraints.Min;
import java.util.List;

@Tag(name = "Акции")
@RestController
@Validated
@SecurityRequirement(name = "bearerAuth")
public class ActionController {

    final ActionService service;
    final Converter con;

    @Autowired
    public ActionController(ActionService service, Converter converter) {
        this.service = service;
        this.con = converter;
    }

    @Operation(summary = "Создание акции")
    @PostMapping("/admin/actions")
    ActionDto.Base.Full createAction(@RequestBody
                                @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "акция")
                                ActionDto.CreateUpdate actionDto) {

//     service.createAction(con.fromDto(actionDto));
        return null;
    }


    @Operation(summary = "Получение акции")
    @GetMapping({"/client/actions/{id}", "/admin/actions/{id}"})
    ActionDto.Base.Full getAction(@PathVariable
                            @Parameter(description = "id")
                            @Min(0) int id) {
        return con.toFullDto(service.getAction(id));
    }

    @Operation(summary = "Изменение акции")
    @PutMapping("/admin/actions/{id}")
    ActionDto.Base.Full updateAction(@RequestBody
                               @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "акция")
                               ActionDto.CreateUpdate actionDto,
                               @PathVariable
                               @Parameter(description = "id")
                               @Min(0) int id) {
        Action action = service.getAction(id);
        action.setId(id);
        return con.toFullDto(action);
    }


    @Operation(summary = "Удаление акции")
    @DeleteMapping({"/admin/actions/{id}",})
    ApiActionResponseDto deleteAction(@PathVariable
                                      @Parameter(description = "id")
                                      @Min(0) int id) {
        return null;
    }


    @Operation(summary = "Получение списка акций")
    @GetMapping({"/client/actions", "/admin/actions"})
    List<ActionDto.Base.Small> getActions() {
        return con.toActionsDto(service.getActions());
    }

}