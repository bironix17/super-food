package ru.bironix.super_food.models.responses;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;
import ru.bironix.super_food.models.action.SmallActionDto;

import java.util.List;

@Schema(description = "Акции в массиве")
@Data
@Builder
public class ActionsResponseDto {

    @NonNull
    List<SmallActionDto> actions;
}

