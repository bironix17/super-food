package ru.bironix.super_food.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.bironix.super_food.dtos.DeliveryInformationDto;

@Tag(name = "Дополнительная информация")
@RestController
@Validated
@SecurityRequirement(name = "bearerAuth")
public class InformationController {

    @Operation(summary = "Получить информацию о доставке")
    @GetMapping(value = {"/client/information/delivery",
            "/admin/information/delivery",
            "/deliveryman/information/delivery"})
    DeliveryInformationDto getDeliveryInformation() {
        return new DeliveryInformationDto(200);
    }

}
