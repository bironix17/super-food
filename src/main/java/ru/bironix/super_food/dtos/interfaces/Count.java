package ru.bironix.super_food.dtos.interfaces;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public interface Count {
    @NotNull @Min(1)
    Integer getCount();
}
