package ru.bironix.super_food.exceptions;

import lombok.Getter;
import ru.bironix.super_food.constants.ApiError;

import java.util.List;

@Getter
public class AddressRequiredException extends RuntimeException {

    public AddressRequiredException() {
        super(ApiError.ADDRESS_REQUIRED.name());
    }
}
