package ru.bironix.super_food.dtos.interfaces;

import ru.bironix.super_food.dtos.order.WayToGetDto;

import javax.validation.constraints.NotNull;

public interface WayToGet {
    @NotNull
    WayToGetDto getWayToGet();
}
