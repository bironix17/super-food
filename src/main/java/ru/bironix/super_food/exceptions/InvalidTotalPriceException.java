package ru.bironix.super_food.exceptions;

import lombok.Getter;

import java.util.List;

@Getter
public class InvalidTotalPriceException extends RuntimeException {

    public InvalidTotalPriceException() {
        super("Ошибка вычисления общей стоимости заказа");
    }
}
