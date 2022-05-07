package ru.bironix.super_food.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.bironix.super_food.converters.Converter;
import ru.bironix.super_food.dtos.order.OrderDto;
import ru.bironix.super_food.dtos.request.createOrder.OrderRequestDto;
import ru.bironix.super_food.services.OrderService;
import ru.bironix.super_food.services.PersonService;

import javax.validation.constraints.Min;
import java.util.List;

import static ru.bironix.super_food.controllers.utils.ControllerUtils.getUsernameFromSecurityContext;

@Tag(name = "Заказ")
@RestController
@Validated
@SecurityRequirement(name = "bearerAuth")
public class OrderController {

    private final OrderService service;
    private final Converter con;
    private final PersonService personService;

    @Autowired
    public OrderController(OrderService service, Converter con, PersonService personService) {
        this.service = service;
        this.con = con;
        this.personService = personService;
    }

    @Operation(summary = "Получить заказ")
    @GetMapping("/my/orders/{id}")
    @ResponseBody
    OrderDto getOrder(@PathVariable @Parameter(description = "id") @Min(0) int id) {
        return con.toDto(service.getOrder(id));
    }

    @Operation(summary = "Получить заказы пользователя. Id указывать id в дальнейшем не придётся")
    @GetMapping("/my/orders")
    @ResponseBody
    List<OrderDto> getOrders() {
        var username = getUsernameFromSecurityContext();
        var person = personService.getByUsername(username);
        return con.toOrdersDto(service.getOrdersForPerson(person));
    }

    @Operation(summary = "совершить заказ", description = "Корректный пример для поля deliveryTime = 10:20")
    @PostMapping("/my/orders")
    @ResponseBody
    OrderDto createOrder(@RequestBody
                         @io.swagger.v3.oas.annotations.parameters.RequestBody()
                         OrderRequestDto order) {

        var username = getUsernameFromSecurityContext();
        var person = personService.getByUsername(username);

        order.setClient(con.toDto(person));

        var createdOrder = service.createOrder(con.fromDto(order));
        return con.toDto(createdOrder);
    }
}