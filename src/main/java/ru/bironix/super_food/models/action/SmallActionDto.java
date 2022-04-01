package ru.bironix.super_food.models.action;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.SuperBuilder;

@Schema(description = "Акция")
@Data
@SuperBuilder
public class SmallActionDto extends AbstractActionDto {

}
