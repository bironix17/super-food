package ru.bironix.super_food.controllers.person;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.bironix.super_food.converters.Converter;
import ru.bironix.super_food.security.log.SecurityLogger;
import ru.bironix.super_food.store.db.models.person.Person;
import ru.bironix.super_food.dtos.person.PersonDto;
import ru.bironix.super_food.dtos.response.ApiActionResponseDto;
import ru.bironix.super_food.services.PersonService;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import static ru.bironix.super_food.controllers.utils.ControllerUtils.getPersonIdFromSecurityContext;

@Tag(name = "Пользователь")
@RestController
@Validated
@RequestMapping("/admin")
@SecurityRequirement(name = "bearerAuth")
public class PersonController {

    private final PersonService service;
    private final Converter con;
    private final SecurityLogger securityLogger;

    @Autowired
    public PersonController(PersonService personService,
                            SecurityLogger securityLogger,
                            Converter converter) {
        this.service = personService;
        this.con = converter;
        this.securityLogger = securityLogger;
    }

    @Operation(summary = "Создать пользователя")
    @PostMapping("/persons")
    PersonDto.BaseForAdmin createPerson(@RequestBody
                                @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "пользователь")
                                @Valid PersonDto.CreateUpdateForAdmin personDto) {
        var person = service.createPerson(con.fromDto(personDto));
        var currentId = getPersonIdFromSecurityContext();
        securityLogger.createPerson(currentId, person.getId());
        return con.toPersonBaseForAdminDto(person);
    }


    @Operation(summary = "Получить информацию о пользователе")
    @GetMapping("/persons/{id}")
    PersonDto.BaseForAdmin getPerson(@PathVariable
                                     @Parameter(description = "id пользователя")
                                     @NotNull @Min(0) int id) {
        var personDto = con.toPersonBaseForAdminDto(service.getPerson(id));
        var currentId = getPersonIdFromSecurityContext();
        securityLogger.getPersonInformation(currentId, id);
        return personDto;
    }

    @Operation(summary = "Обновить информацию о пользователе. Поле id в json можно не заполнять." +
            " Поля которые обновлять не нужно должны быть null")
    @PutMapping("/persons/{id}")
    PersonDto.BaseForAdmin updatePerson(@RequestBody
                                        @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "пользователь")
                                        PersonDto.CreateUpdateForAdmin personDto,
                                        @PathVariable
                                        @Parameter(description = "id пользователя")
                                        @NotNull @Min(0) int id) {

        Person person = con.fromDto(personDto);
        person.setId(id);
        var updatedPerson = service.updatePerson(person);
        var currentId = getPersonIdFromSecurityContext();
        securityLogger.getPersonInformation(currentId, id);
        return con.toPersonBaseForAdminDto(updatedPerson);
    }

    @Operation(summary = "Удалить пользователя")
    @DeleteMapping("/persons/{id}")
    ApiActionResponseDto deletePerson(@PathVariable
                                      @Parameter(description = "id пользователя")
                                      @NotNull @Min(0) int id) {
        service.deletePerson(id);
        var currentId = getPersonIdFromSecurityContext();
        securityLogger.deletePerson(currentId, id);
        return new ApiActionResponseDto(true);
    }

}
