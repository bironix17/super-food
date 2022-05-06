package ru.bironix.super_food.dtos.dish;

import com.fasterxml.jackson.annotation.JsonValue;
import io.swagger.annotations.ApiModel;

@ApiModel
public enum CategoryType {
    BURGERS("Бургеры"),
    ROLLS("Роллы"),
    PIZZA("Пицца"),
    DRINKS("Напитки"),
    SAUCES("Соусы"),
    COMBO("Комбо");

    private String rusName;

    CategoryType(String rusName) {
        this.rusName = rusName;
    }

    @JsonValue
    public String getRusName() {
        return rusName;
    }
}
