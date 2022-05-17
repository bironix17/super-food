package ru.bironix.super_food.dtos.order;

import com.fasterxml.jackson.annotation.JsonValue;
import io.swagger.annotations.ApiModel;

@ApiModel
public enum OrderStatusDto {
    AWAITING_PAYMENT("Ожидается оплата"),
    EXPECTS("Ожидает исполнения"),
    COOK("Готовится"),
    COOKED("Приготовлено"),
    DELIVERING("Доставляется"),
    COMPLETED("Завершен"),
    CANCELED("Отменён");


    private String rusName;

    OrderStatusDto(String rusName) {
        this.rusName = rusName;
    }

    @JsonValue
    public String getRusName() {
        return rusName;
    }
}
