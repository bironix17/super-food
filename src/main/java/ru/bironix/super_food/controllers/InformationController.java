package ru.bironix.super_food.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.bironix.super_food.dtos.DeliveryInformationDto;
import ru.bironix.super_food.services.InformationService;

@Tag(name = "Дополнительная информация")
@RestController
@Validated
@SecurityRequirement(name = "bearerAuth")
public class InformationController {

    private final InformationService service;

    @Autowired
    public InformationController(InformationService service) {
        this.service = service;
    }

    @Operation(summary = "Получить информацию о доставке")
    @GetMapping(value = {"/client/information/delivery",
            "/admin/information/delivery",
            "/deliveryman/information/delivery"})
    DeliveryInformationDto getDeliveryInformation() {

        return new DeliveryInformationDto(service.getDeliveryPrice());
    }

}
