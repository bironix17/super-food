package ru.bironix.super_food.controllers;

import io.swagger.annotations.ApiParam;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.bironix.super_food.converters.Converter;
import ru.bironix.super_food.dtos.AddressDto;
import ru.bironix.super_food.dtos.PersonDto;
import ru.bironix.super_food.dtos.responses.DeleteResponse;
import ru.bironix.super_food.services.PersonService;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

@Tag(name = "Пользователь")
@RestController
@Validated
@SecurityRequirement(name = "bearerAuth")
//@PreAuthorize("hasAnyRole('ALL')")
public class PersonController {

    @Autowired
    public PersonController(PersonService personService, Converter converter) {
        this.personService = personService;
        this.con = converter;
    }

    final PersonService personService;
    final Converter con;

    @Operation(summary = "Получить инфу о себе", description = "пока получает id, для возможности тестирования на фронте." +
            "Потом id передавать не нужно будет.")
    @GetMapping("/getMe/{id}")
    @ResponseBody
    PersonDto getMe(@PathVariable @Parameter(description = "id пользователя")  @Min(0) int id) {
        return con.toDto(personService.getMe(id));
    }

    @Operation(summary = "Обновить информацию о себе. Поля которые обновлять не нужно должны быть null. Пока что важно присутствие id")
    @PutMapping("/updateMe")
    @ResponseBody
    PersonDto updatePerson(@Valid @RequestBody @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "сущность пользователя") PersonDto person) {
        var updatedPerson = personService.updatePerson(con.fromDto(person));
        return con.toDto(updatedPerson);
    }


    @Operation(summary = "Добавить адрес для пользователя")
    @PutMapping("/addAddress/{id}")
    @ResponseBody
    AddressDto addAddress(@PathVariable @Parameter(description = "id пользователя") @Min(0) int id,
                             @RequestParam @ApiParam(name = "добавляемый адрес") @NotBlank String address) {
        var newAddress = personService.addAddressForPerson(id, address);
        return con.toDto(newAddress);
    }

    @Operation(summary = "Удалить адрес для пользователя. Возвращаемое значение возможно изменится")
    @DeleteMapping("/deleteAddress/{id}")
    @ResponseBody
    DeleteResponse updateMe(@PathVariable @Parameter(description = "id адреса") @Min(0) int id) {
        personService.deleteAddress(id);
        return new DeleteResponse(true);
    }
}
