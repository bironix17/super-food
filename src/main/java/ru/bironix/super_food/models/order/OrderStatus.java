package ru.bironix.super_food.models.order;

import io.swagger.annotations.ApiModel;

@ApiModel
public enum OrderStatus {
    ACCEPTED, COOKING ,IN_DELIVERY, COMPLETED, CANCELED;
}
