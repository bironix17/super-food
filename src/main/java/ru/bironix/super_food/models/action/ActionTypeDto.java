package ru.bironix.super_food.models.action;

import io.swagger.annotations.ApiModel;

@ApiModel()
public enum ActionTypeDto {
    DISH_DISCOUNT,
    COMBO_DISCOUNT,
    COMBO_GIFT;
}