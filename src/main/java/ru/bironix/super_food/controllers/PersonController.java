package ru.bironix.super_food.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.bironix.super_food.converters.Converter;
import ru.bironix.super_food.dtos.PersonDto;
import ru.bironix.super_food.services.PersonService;

@Tag(name = "Пользователь")
@RestController

public class PersonController {

    @Autowired
    public PersonController(PersonService personService, Converter converter) {
        this.personService = personService;
        this.converter = converter;
    }

    final PersonService personService;
    final Converter converter;


    @Operation(summary = "Получить инфу о себе", description = "пока получает id, для возможности тестирования на фронте." +
            "Потом id передавать не нужно будет.")
    @GetMapping("/getMe/{id}")
    @ResponseBody
    PersonDto getMe(@PathVariable @Parameter(description = "id пользователя") int id) {
        return converter.toDto(personService.getMe(id));
    }


}
