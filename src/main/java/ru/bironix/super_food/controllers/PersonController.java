package ru.bironix.super_food.controllers;

import io.swagger.annotations.ApiParam;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.bironix.super_food.converters.Converter;
import ru.bironix.super_food.dtos.person.AddressDto;
import ru.bironix.super_food.dtos.person.PersonDto;
import ru.bironix.super_food.dtos.responses.DeleteResponse;
import ru.bironix.super_food.services.PersonService;

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

    @Operation(summary = "Получить инфу о себе")
    @GetMapping("/getMe")
    @ResponseBody
    PersonDto getPerson() {
        var username = getUsernameFromSecurityContext();
        return con.toDto(personService.getByUsername(username));
    }

    @Operation(summary = "Обновить информацию о себе. Поля которые обновлять не нужно должны быть null")
    @PutMapping("/updateMe")
    @ResponseBody
    PersonDto updatePerson(@RequestBody @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "сущность пользователя") PersonDto person) {
        var username = getUsernameFromSecurityContext();
        var personBd = personService.getByUsername(username);
        person.setId(personBd.getId());
        var updatedPerson = personService.updatePerson(con.fromDto(person));
        return con.toDto(updatedPerson);
    }


    @Operation(summary = "Добавить адрес для пользователя")
    @PutMapping("/addAddress")
    @ResponseBody
    AddressDto addAddress(@RequestParam @ApiParam(name = "добавляемый адрес") @NotBlank String address) {
        var username = getUsernameFromSecurityContext();
        var newAddress = personService.addAddressForPerson(username, address);
        return con.toDto(newAddress);
    }

    @Operation(summary = "Удалить адрес для пользователя")
    @DeleteMapping("/deleteAddress/{id}")
    @ResponseBody
    DeleteResponse deleteAddress(@PathVariable @Parameter(description = "id адреса") @Min(0) int id) {
        personService.deleteAddress(id);
        return new DeleteResponse(true);
    }

    private String getUsernameFromSecurityContext() {
        var user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return user.getUsername();
    }
}
