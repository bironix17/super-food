package ru.bironix.super_food.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.bironix.super_food.converters.Converter;
import ru.bironix.super_food.dtos.DeliveryInformationDto;
import ru.bironix.super_food.services.InformationService;

import javax.validation.Valid;

@Tag(name = "Дополнительная информация")
@RestController
@Validated
@SecurityRequirement(name = "bearerAuth")
public class InformationController {

    private final InformationService service;
    private final Converter con;

    @Autowired
    public InformationController(InformationService service, Converter con) {
        this.service = service;
        this.con = con;
    }

    @Operation(summary = "Получить информацию о доставке")
    @GetMapping({"/client/information/delivery",
            "/admin/information/delivery",
            "/deliveryman/information/delivery"})
    DeliveryInformationDto getDeliveryInformation() {
        return con.toDto(service.getDeliveryInformation());
    }


    @Operation(summary = "Изменить информацию о доставке")
    @PostMapping({"/admin/information/delivery"})
    DeliveryInformationDto updateDeliveryInformation(@RequestBody
                                                     @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Информация о доставке")
                                                     @Valid DeliveryInformationDto deliveryInformationDto) {

        return con.toDto(
                service.updateDeliveryInformation(
                        con.fromDto(
                                deliveryInformationDto)));
    }
}
