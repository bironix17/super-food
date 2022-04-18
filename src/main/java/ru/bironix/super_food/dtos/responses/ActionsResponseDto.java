package ru.bironix.super_food.dtos.responses;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;
import ru.bironix.super_food.dtos.action.SmallActionDto;

import java.util.List;


@Schema(description = "Акции в массиве")
@Data
@Builder
@AllArgsConstructor
public class ActionsResponseDto {

    @NonNull
    List<SmallActionDto> actions;
}

