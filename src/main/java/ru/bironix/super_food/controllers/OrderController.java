package ru.bironix.super_food.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.bironix.super_food.converters.Converter;
import ru.bironix.super_food.security.log.SecurityLogger;
import ru.bironix.super_food.store.db.models.order.Order;
import ru.bironix.super_food.dtos.order.OrderDto;
import ru.bironix.super_food.dtos.order.OrderStatusDto;
import ru.bironix.super_food.dtos.person.PersonDto;
import ru.bironix.super_food.dtos.response.ApiActionResponseDto;
import ru.bironix.super_food.services.OrderService;
import ru.bironix.super_food.services.PersonService;

import javax.validation.Valid;
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
    private final SecurityLogger securityLogger;

    @Autowired
    public OrderController(OrderService service,
                           Converter con,
                           SecurityLogger securityLogger,
                           PersonService personService) {
        this.service = service;
        this.con = con;
        this.personService = personService;
        this.securityLogger = securityLogger;
    }

    @Operation(summary = "Создать заказ", description = "Корректный пример для поля deliveryTime = 10:20")
    @PostMapping("/admin/orders/person/{personId}")
    @ResponseBody
    OrderDto.Base.Full createOrder(@RequestBody
                                   @io.swagger.v3.oas.annotations.parameters.RequestBody()
                                   @Valid OrderDto.CreateUpdate orderDto,
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
        var order = service.getOrder(id);
        var currentPersonId = getPersonIdFromSecurityContext();
        securityLogger.getOrder(currentPersonId, id);
        return con.toFullDto(order);
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
        var updatedOrder = service.updateOrder(order);
        var currentPersonId = getPersonIdFromSecurityContext();
        securityLogger.changeOrder(currentPersonId, id);
        return con.toFullDto(updatedOrder);
    }

    @Operation(summary = "Удаление заказа")
    @DeleteMapping("/admin/orders/{id}")
    ApiActionResponseDto deleteOrder(@PathVariable
                                     @Parameter(description = "id заказа")
                                     @Min(0) int id) {
        service.deleteOrder(id);
        var currentPersonId = getPersonIdFromSecurityContext();
        securityLogger.deleteOrder(currentPersonId, id);
        return new ApiActionResponseDto(true);
    }

    @Operation(summary = "Получить мой заказ")
    @GetMapping("/client/my/orders/{id}")
    @ResponseBody
    OrderDto.Base.Full getOrderForMy(@PathVariable
                                     @Parameter(description = "id")
                                     @Min(0) int id) {
        var order = service.getOrder(id);
        var currentPersonId = getPersonIdFromSecurityContext();
        securityLogger.deleteOrder(currentPersonId, id);
        return con.toFullDto(order);
    }

    @Operation(summary = "Получить мои заказы. Размер страницы равен 10")
    @GetMapping("/client/my/orders")
    @ResponseBody
    List<OrderDto.Base.Small> getOrdersForMy(@RequestParam(value = "page", defaultValue = "0")
                                             @Parameter(description = "Запрашиваемый номер страницы")
                                             Integer pageNumber) {
        var id = getPersonIdFromSecurityContext();
        var person = personService.getPerson(id);
        var orders = service.getOrdersForPerson(person, pageNumber);
        securityLogger.getOrders(id, orders);
        return con.toOrdersDto(orders);
    }

    @Operation(summary = "Совершить заказ для меня", description = "Корректный пример для поля deliveryTime = 10:20")
    @PostMapping("/client/my/orders")
    @ResponseBody
    OrderDto.Base.Full createOrderForMy(@RequestBody
                                        @io.swagger.v3.oas.annotations.parameters.RequestBody()
                                        @Valid OrderDto.CreateUpdate orderDto) {

        var id = getPersonIdFromSecurityContext();
        var order = con.fromDto(orderDto);

        order.setClient(con.fromDto(PersonDto.Bind.builder()
                .id(id)
                .build()));

        var createdOrder = service.createOrder(order);
        securityLogger.createOrder(id, createdOrder.getId());
        return con.toFullDto(createdOrder);
    }

    @Operation(summary = "Изменение статуса заказа")
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
                .status(con.fromDto(status))
                .build();

        var updatedOrder = service.updateOrder(order);
        var currentPersonId = getPersonIdFromSecurityContext();
        securityLogger.changeOrder(currentPersonId, order.getId());
        return con.toFullDto(updatedOrder);
    }

    @Operation(summary = "Получение незавершённых заказов")
    @GetMapping({"/deliveryman/activeOrders",
            "/cook/activeOrders",
            "/admin/activeOrders"})
    List<OrderDto.Base.Small> getActiveOrders(@RequestParam(value = "page", defaultValue = "0")
                                              @Parameter(description = "Запрашиваемый номер страницы")
                                              Integer pageNumber) {
        var orders = service.getActiveOrders(pageNumber);
        var id = getPersonIdFromSecurityContext();
        securityLogger.getOrders(id, orders);
        return con.toOrdersDto(orders);
    }

    @Operation(summary = "Получение заказов по конкретному статусу")
    @GetMapping({"/deliveryman/orders/status/{status}",
            "/cook/activeOrders",
            "/admin/orders/status/{status}"})
    List<OrderDto.Base.Small> getOrdersByStatus(@PathVariable
                                                @Parameter(description = "статус")
                                                OrderStatusDto status,
                                                @RequestParam(value = "page", defaultValue = "0")
                                                @Parameter(description = "Запрашиваемый номер страницы")
                                                Integer pageNumber) {
        var orders = service.getOrdersByStatus(con.fromDto(status), pageNumber);
        var id = getPersonIdFromSecurityContext();
        securityLogger.getOrders(id, orders);
        return con.toOrdersDto(orders);
    }
}