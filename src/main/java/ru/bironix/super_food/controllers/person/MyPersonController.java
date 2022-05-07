package ru.bironix.super_food.controllers.person;

import io.swagger.annotations.ApiParam;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.bironix.super_food.converters.Converter;
import ru.bironix.super_food.dtos.person.AddressDto;
import ru.bironix.super_food.dtos.person.PersonDto;
import ru.bironix.super_food.dtos.response.ApiActionResponseDto;
import ru.bironix.super_food.services.PersonService;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import java.util.List;

import static ru.bironix.super_food.controllers.utils.ControllerUtils.getUsernameFromSecurityContext;

@Tag(name = "Пользователь. Информация о себе")
@RestController
@Validated
@RequestMapping("/client")
@SecurityRequirement(name = "bearerAuth")
public class MyPersonController {

    private final PersonService service;
    private final Converter con;

    @Autowired
    public MyPersonController(PersonService personService, Converter converter) {
        this.service = personService;
        this.con = converter;
    }

    @Operation(summary = "Получить информацию о себе")
    @GetMapping("/my")
    PersonDto getMy() {
        var username = getUsernameFromSecurityContext();
        return con.toDto(service.getByUsername(username));
    }

    @Operation(summary = "Обновить информацию о себе. Поля которые обновлять не нужно должны быть null")
    @PutMapping("/my")
    PersonDto updateMy(@RequestBody
                       @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "пользователь")
                       PersonDto person) {
        var username = getUsernameFromSecurityContext();
        var personBd = service.getByUsername(username);
        person.setId(personBd.getId());
        var updatedPerson = service.updatePerson(con.fromDto(person));
        return con.toDto(updatedPerson);
    }


    @Operation(summary = "Добавить адрес для пользователя")
    @PostMapping("/my/addresses")
    AddressDto addAddressForMy(@RequestParam
                               @ApiParam(name = "добавляемый адрес")
                               @NotBlank String address) {
        var username = getUsernameFromSecurityContext();
        var newAddress = service.addAddressForPerson(username, address);
        return con.toDto(newAddress);
    }

    @Operation(summary = "Удалить адрес для пользователя")
    @DeleteMapping("/my/addresses/{id}")
    ApiActionResponseDto deleteAddressForMy(@PathVariable
                                            @Parameter(description = "id адреса")
                                            @Min(0) int id) {
        service.deleteAddress(id);
        return new ApiActionResponseDto(true);
    }


    @Operation(summary = "Получить закладки пользователя")
    @GetMapping("/my/favorites")
    List<Integer> getFavoritesForMy() {
        var username = getUsernameFromSecurityContext();
        return con.toFavoritesDto(service.getFavoritesForPerson(username));
    }

    @Operation(summary = "Добавить закладку для пользователя")
    @PostMapping("/my/favorites/{dishId}")
    ApiActionResponseDto addFavoritesForMy(@PathVariable
                                           @Parameter(description = "id блюда")
                                           @Min(0) int dishId) {
        var username = getUsernameFromSecurityContext();
        service.addFavoritesForPerson(username, dishId);
        return new ApiActionResponseDto(true);
    }

    @Operation(summary = "Удалить закладку у пользователя")
    @DeleteMapping("/my/favorites/{dishId}")
    ApiActionResponseDto deleteFavoritesForMy(@PathVariable
                                              @Parameter(description = "id блюда")
                                              @Min(0) int dishId) {
        var username = getUsernameFromSecurityContext();
        service.deleteFavoritesForPerson(username, dishId);
        return new ApiActionResponseDto(true);
    }

}
