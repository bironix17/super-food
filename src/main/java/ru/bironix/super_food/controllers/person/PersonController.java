package ru.bironix.super_food.controllers.person;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.bironix.super_food.converters.Converter;
import ru.bironix.super_food.dtos.person.PersonDto;
import ru.bironix.super_food.services.PersonService;

import javax.validation.constraints.Min;

@Tag(name = "Пользователь")
@RestController
@Validated
@RequestMapping("/admin")
@SecurityRequirement(name = "bearerAuth")
public class PersonController {

    private final PersonService service;
    private final Converter con;

    @Autowired
    public PersonController(PersonService personService, Converter converter) {
        this.service = personService;
        this.con = converter;
    }

    @Operation(summary = "Создать пользователя")
    @PostMapping("/persons")
    PersonDto createPerson(@RequestBody
                           @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "пользователь")
                           PersonDto person) {
        return null;
    }


    @Operation(summary = "Получить информацию о пользователе")
    @GetMapping("/persons/{id}")
    PersonDto getPerson(@PathVariable
                        @Parameter(description = "id пользователя")
                        @Min(0) int id) {
        return null;
    }

    @Operation(summary = "Обновить информацию о пользователе. Поля которые обновлять не нужно должны быть null")
    @PutMapping("/persons/{id}")
    PersonDto updatePerson(@RequestBody
                           @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "пользователь")
                           PersonDto person,
                           @PathVariable
                           @Parameter(description = "id пользователя")
                           @Min(0) int id) { // TODO @VALID
        person.setId(id);
        return null;
    }

    @Operation(summary = "Удалить пользователя")
    @DeleteMapping("/persons/{id}")
    ResponseStatus deletePerson(@PathVariable
                                @Parameter(description = "id пользователя")
                                @Min(0) int id) { // TODO @VALID
        return null;
    }

}
