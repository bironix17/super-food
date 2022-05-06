package ru.bironix.super_food.controllers;

import io.swagger.annotations.ApiParam;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.bironix.super_food.converters.Converter;
import ru.bironix.super_food.dtos.person.AddressDto;
import ru.bironix.super_food.dtos.person.PersonDto;
import ru.bironix.super_food.dtos.response.DeleteResponseDto;
import ru.bironix.super_food.services.PersonService;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

import static ru.bironix.super_food.controllers.utils.ControllerUtils.getUsernameFromSecurityContext;

@Tag(name = "Пользователь")
@RestController
@Validated
@SecurityRequirement(name = "bearerAuth")
public class PersonController {

    private final PersonService personService;
    private final Converter con;

    @Autowired
    public PersonController(PersonService personService, Converter converter) {
        this.personService = personService;
        this.con = converter;
    }

    @Operation(summary = "Получить инфу о себе")
    @GetMapping("/getMe")
    PersonDto getPerson() {
        var username = getUsernameFromSecurityContext();
        return con.toDto(personService.getByUsername(username));
    }

    @Operation(summary = "Обновить информацию о себе. Поля которые обновлять не нужно должны быть null")
    @PutMapping("/updateMe")
    PersonDto updatePerson(@RequestBody @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "сущность пользователя") PersonDto person) {
        var username = getUsernameFromSecurityContext();
        var personBd = personService.getByUsername(username);
        person.setId(personBd.getId());
        var updatedPerson = personService.updatePerson(con.fromDto(person));
        return con.toDto(updatedPerson);
    }


    @Operation(summary = "Добавить адрес для пользователя")
    @PutMapping("/addAddress")
    AddressDto addAddress(@RequestParam @ApiParam(name = "добавляемый адрес") @NotBlank String address) {
        var username = getUsernameFromSecurityContext();
        var newAddress = personService.addAddressForPerson(username, address);
        return con.toDto(newAddress);
    }

    @Operation(summary = "Удалить адрес для пользователя")
    @DeleteMapping("/deleteAddress/{id}")
    DeleteResponseDto deleteAddress(@PathVariable @Parameter(description = "id адреса") @Min(0) int id) {
        personService.deleteAddress(id);
        return new DeleteResponseDto(true);
    }
}
