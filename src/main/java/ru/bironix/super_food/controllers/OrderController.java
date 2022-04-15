package ru.bironix.super_food.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.bironix.super_food.dtos.order.OrderDto;
import ru.bironix.super_food.services.OrderService;

@Tag(name = "Заказ")
@RestController

public class OrderController {

    @Autowired
    OrderService orderService;

    @Operation(summary = "Получить заказ", description = "**Пока возвращает захардоженный объект !!!!!**")
    @GetMapping("/order/{id}")
    @ResponseBody
    OrderDto getOrder(@PathVariable @Parameter(description = "id") int id) {
        return orderService.getOrder(id);
    }


}