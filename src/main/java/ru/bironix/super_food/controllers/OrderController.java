package ru.bironix.super_food.controllers;

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
import ru.bironix.super_food.dtos.order.OrderDto;
import ru.bironix.super_food.dtos.responses.OrdersResponseDto;
import ru.bironix.super_food.services.OrderService;
import ru.bironix.super_food.services.PersonService;

import javax.validation.constraints.Min;

@Tag(name = "Заказ")
@RestController
@Validated
@SecurityRequirement(name = "bearerAuth")
public class OrderController {


    private static final String EXAMPLE_PARAM_CREATE_USER = "https://docs.google.com/document/d/1M4YVr78hUMci5bZagc1hIrtIwYgrkkR1N1UM1sdyZ9I/edit?usp=sharing";


    @Autowired
    public OrderController(OrderService orderService, Converter con) {
        this.service = orderService;
        this.con = con;
    }

    final OrderService service;
    final Converter con;

    @Autowired
    private PersonService personService;

    @Operation(summary = "Получить заказ")
    @GetMapping("/order/{id}")
    @ResponseBody
    OrderDto getOrder(@PathVariable @Parameter(description = "id") @Min(0) int id) {
        return con.toDto(service.getOrder(id));
    }

    @Operation(summary = "Получить заказы пользователя. Id указывать id в дальнейшем не придётся")
    @GetMapping("/myOrders")
    @ResponseBody
    OrdersResponseDto getOrders() {
        var username = getUsernameFromSecurityContext();
        var person = personService.getByUsername(username);
        return con.toOrdersResponse(service.getOrdersForPerson(person));
    }

    @Operation(summary = "совершить заказ", description = "пример запроса: " + EXAMPLE_PARAM_CREATE_USER)
    @PostMapping("/createOrder")
    @ResponseBody
    OrderDto createOrder(@RequestBody
                         @io.swagger.v3.oas.annotations.parameters.RequestBody()
                         OrderDto order) {

        var username = getUsernameFromSecurityContext();
        var person = personService.getByUsername(username);

        order.setClient(con.toDto(person));

        var createdOrder = service.createOrder(con.fromDto(order));
        return con.toDto(createdOrder);
    }


    private String getUsernameFromSecurityContext() {
        var user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return user.getUsername();
    }
}