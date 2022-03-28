package ru.bironix.super_food.models.action;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.SuperBuilder;
import org.springframework.lang.Nullable;
import ru.bironix.super_food.models.ComboDishesDto;

import java.util.ArrayList;
import java.util.List;

@Schema(description = "Акция")
@Data
@SuperBuilder
public class SmallActionDto extends AbstractActionDto {

}
