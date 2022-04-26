package ru.bironix.super_food.dtos.responses;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import ru.bironix.super_food.dtos.order.OrderDto;

import java.util.List;

@Schema(description = "Список заказов")
@Data
@Builder
@AllArgsConstructor
public class OrdersResponseDto {
    List<OrderDto> orders;
}
