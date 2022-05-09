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
import ru.bironix.super_food.dtos.order.OrderStatusDto;
import ru.bironix.super_food.dtos.person.PersonDto;
import ru.bironix.super_food.dtos.response.ApiActionResponseDto;
import ru.bironix.super_food.services.OrderService;
import ru.bironix.super_food.services.PersonService;

import javax.validation.constraints.Min;
import java.util.List;

import static ru.bironix.super_food.controllers.utils.ControllerUtils.getPersonIdFromSecurityContext;


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

    @Operation(summary = "Создать заказ", description = "Корректный пример для поля deliveryTime = 10:20")
    @PostMapping("/admin/orders")
    @ResponseBody
    OrderDto.Base.Full createOrder(@RequestBody
                                   @io.swagger.v3.oas.annotations.parameters.RequestBody()
                                   OrderDto.CreateUpdate orderDto) {

        return null;
    }

    @Operation(summary = "Получение заказа")
    @GetMapping("/admin/orders/{id}")
    OrderDto.Base.Full getOrder(@PathVariable
                                @Parameter(description = "id заказа")
                                @Min(0) int id) {
        return null;
    }

    @Operation(summary = "Изменение заказа")
    @PutMapping("/admin/orders/{id}")
    OrderDto.Base.Full updateOrder(@RequestBody
                                   @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Заказ")
                                   OrderDto.CreateUpdate orderDto,
                                   @PathVariable
                                   @Parameter(description = "id заказа")
                                   @Min(0) int id) {
        return null;
    }

    @Operation(summary = "Удаление заказа")
    @DeleteMapping("/admin/orders/{id}")
    ApiActionResponseDto deleteOrder(@PathVariable
                                     @Parameter(description = "id заказа")
                                     @Min(0) int id) {
        return null;
    }


    @Operation(summary = "Получить мой заказ")
    @GetMapping("/client/my/orders/{id}")
    @ResponseBody
    OrderDto.Base.Full getOrderForMy(@PathVariable @Parameter(description = "id") @Min(0) int id) {//TODO прикрутить проверку владельца заказа
        return con.toFullDto(service.getOrder(id));
    }

    @Operation(summary = "Получить мои заказы")
    @GetMapping("/client/my/orders")
    @ResponseBody
    List<OrderDto.Base.Small> getOrdersForMy() {
        var id = getPersonIdFromSecurityContext();
        var person = personService.getPersonById(id);
        return con.toOrdersDto(service.getOrdersForPerson(person));
    }

    @Operation(summary = "Совершить заказ для меня", description = "Корректный пример для поля deliveryTime = 10:20")
    @PostMapping("/client/my/orders")
    @ResponseBody
    OrderDto.Base.Full createOrderForMy(@RequestBody
                                        @io.swagger.v3.oas.annotations.parameters.RequestBody()
                                        OrderDto.CreateUpdate orderDto) {

        var id = getPersonIdFromSecurityContext();
//        var person = personService.getPersonById(id);
        var order = con.fromDto(orderDto);

        order.setClient(con.fromDto(PersonDto.Bind.builder()
                .id(id)
                .build()));

        var createdOrder = service.createOrder(order);
        return con.toFullDto(createdOrder);
    }


    @Operation(summary = "Изменение статуса блюда")
    @PutMapping({"/deliveryman/orders/{id}/status/{status}", "/admin/orders/{id}/status/{status}"})
    OrderDto.Base.Full updateOrder(@PathVariable
                                   @Parameter(description = "id заказа")
                                   @Min(0) int id,
                                   @PathVariable
                                   @Parameter(description = "статус")
                                   OrderStatusDto status) {
        return null;
    }

    @Operation(summary = "Получение незавершённых заказов")
    @GetMapping({"/deliveryman/activeOrders", "/admin/activeOrders"})
    List<OrderDto.Base.Small> getActiveOrders() {
        return null;
    }

    @Operation(summary = "Получение заказов по конкретному статусу")
    @GetMapping({"/deliveryman/orders/status/{status}", "/admin/orders/status/{status}"})
    List<OrderDto.Base.Small> getOrdersByStatus(@PathVariable
                                                @Parameter(description = "статус")
                                                OrderStatusDto status) {
        return null;
    }
}