package ru.bironix.super_food.exceptions;

import lombok.Getter;
import ru.bironix.super_food.constants.ApiError;

@Getter
public class InvalidTotalPriceException extends RuntimeException {

    public InvalidTotalPriceException() {
        super(ApiError.INVALID_TOTAL_PRICE.name());
    }
}
