package ru.bironix.super_food.dtos.order;

import io.swagger.annotations.ApiModel;

@ApiModel
public enum StatusDto {
    AWAITING_PAYMENT, EXPECTS, COOK, COOKED, DELIVERED, COMPLETED, CANCELED
}
