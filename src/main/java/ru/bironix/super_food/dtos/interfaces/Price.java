package ru.bironix.super_food.dtos.interfaces;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public interface Price {
    @NotNull
    @NotNull @Min(0)
    Integer getPrice();
}
