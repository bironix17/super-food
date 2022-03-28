package ru.bironix.super_food.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import ru.bironix.super_food.models.action.FullActionDto;
import ru.bironix.super_food.models.action.SmallActionDto;
import ru.bironix.super_food.models.order.OrderDto;
import ru.bironix.super_food.services.ActionService;
import ru.bironix.super_food.services.OrderService;

import java.util.List;

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