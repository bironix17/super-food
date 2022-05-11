package ru.bironix.super_food.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.bironix.super_food.converters.Converter;
import ru.bironix.super_food.db.models.order.Order;
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
    @PostMapping("/admin/orders/person/{personId}")
    @ResponseBody
    OrderDto.Base.Full createOrder(@RequestBody
                                   @io.swagger.v3.oas.annotations.parameters.RequestBody()
                                   OrderDto.CreateUpdate orderDto,
                                   @PathVariable
                                   @Parameter(description = "id пользователя")
                                   @Min(0) int personId) {

        var order = con.fromDto(orderDto);
        var person = personService.getPerson(personId);

        order.setClient(person);

        var createdOrder = service.createOrder(order);
        return con.toFullDto(createdOrder);
    }

    @Operation(summary = "Получение заказа")
    @GetMapping({"/deliveryman/orders/{id}",
            "/cook/orders/{id}",
            "/admin/orders/{id}"})
    OrderDto.Base.Full getOrder(@PathVariable
                                @Parameter(description = "id заказа")
                                @Min(0) int id) {
        return con.toFullDto(service.getOrder(id));
    }

    @Operation(summary = "Изменение заказа")
    @PutMapping("/admin/orders/{id}")
    OrderDto.Base.Full updateOrder(@RequestBody
                                   @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Заказ")
                                   OrderDto.CreateUpdate orderDto,
                                   @PathVariable
                                   @Parameter(description = "id заказа")
                                   @Min(0) int id) {
        var order = con.fromDto(orderDto);
        order.setId(id);
        return con.toFullDto(service.updateOrder(order));
    }

    @Operation(summary = "Удаление заказа")
    @DeleteMapping("/admin/orders/{id}")
    ApiActionResponseDto deleteOrder(@PathVariable
                                     @Parameter(description = "id заказа")
                                     @Min(0) int id) {
        service.deleteOrder(id);
        return new ApiActionResponseDto(true);
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
        var person = personService.getPerson(id);
        return con.toOrdersDto(service.getOrdersForPerson(person));
    }

    @Operation(summary = "Совершить заказ для меня", description = "Корректный пример для поля deliveryTime = 10:20")
    @PostMapping("/client/my/orders")
    @ResponseBody
    OrderDto.Base.Full createOrderForMy(@RequestBody
                                        @io.swagger.v3.oas.annotations.parameters.RequestBody()
                                        OrderDto.CreateUpdate orderDto) {

        var id = getPersonIdFromSecurityContext();
        var order = con.fromDto(orderDto);

        order.setClient(con.fromDto(PersonDto.Bind.builder()
                .id(id)
                .build()));

        var createdOrder = service.createOrder(order);
        return con.toFullDto(createdOrder);
    }

    @Operation(summary = "Изменение статуса блюда")
    @PutMapping({"/deliveryman/orders/{id}/status/{status}",
            "/cook/orders/{id}/status/{status}",
            "/admin/orders/{id}/status/{status}"})
    OrderDto.Base.Full updateOrder(@PathVariable
                                   @Parameter(description = "id заказа")
                                   @Min(0) int id,
                                   @PathVariable
                                   @Parameter(description = "статус")
                                   OrderStatusDto status) {
        var order = Order.builder()
                .id(id)
                .orderStatus(con.fromDto(status))
                .build();

        return con.toFullDto(service.updateOrder(order));
    }

    @Operation(summary = "Получение незавершённых заказов")
    @GetMapping({"/deliveryman/activeOrders",
            "/cook/activeOrders",
            "/admin/activeOrders"})
    List<OrderDto.Base.Small> getActiveOrders() {
        return con.toOrdersDto(service.getActiveOrders());
    }

    @Operation(summary = "Получение заказов по конкретному статусу")
    @GetMapping({"/deliveryman/orders/status/{status}",
            "/cook/activeOrders",
            "/admin/orders/status/{status}"})
    List<OrderDto.Base.Small> getOrdersByStatus(@PathVariable
                                                @Parameter(description = "статус")
                                                OrderStatusDto status) {
        return con.toOrdersDto(service.getOrdersByStatus(con.fromDto(status)));
    }
}