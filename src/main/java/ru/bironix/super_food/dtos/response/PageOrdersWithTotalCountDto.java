package ru.bironix.super_food.dtos.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.bironix.super_food.dtos.order.OrderDto;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Страница сущностей с числом полного их количества")
public class PageOrdersWithTotalCountDto {
    List<OrderDto.Base.Small> orders;
    Long totalCount;
}
