package ru.bironix.super_food.models;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class TempDto {

    List<CategoryDto> categories;
}
