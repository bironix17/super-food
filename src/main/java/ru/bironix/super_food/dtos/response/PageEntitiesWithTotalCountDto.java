package ru.bironix.super_food.dtos.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Страница сущностей с числом полного их количества")
public class PageEntitiesWithTotalCountDto {
    List<Object> entities;
    Long totalCount;
}
