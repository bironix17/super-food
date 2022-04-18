package ru.bironix.super_food.dtos.action;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.experimental.SuperBuilder;
import ru.bironix.super_food.dtos.dish.SmallDishDto;

import javax.validation.Valid;
import java.util.List;

@Schema(description = "Акция")
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class FullActionDto extends AbstractActionDto {

    @Valid
    @Schema(description = "Список блюд по акции")
    List<SmallDishDto> dishes;
}