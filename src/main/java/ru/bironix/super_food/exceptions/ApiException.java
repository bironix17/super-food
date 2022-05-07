package ru.bironix.super_food.exceptions;

import lombok.Getter;
import ru.bironix.super_food.constants.ApiError;


@Getter
public class ApiException extends RuntimeException {

    private final ApiError apiError;

    public ApiException(ApiError apiError) {
        super(apiError.name());
        this.apiError = apiError;
    }
}