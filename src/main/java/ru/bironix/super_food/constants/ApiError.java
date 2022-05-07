package ru.bironix.super_food.constants;

import io.swagger.annotations.ApiModel;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(enumAsRef = true, name = "Сообщения об ошибках")
@ApiModel
public enum ApiError {
    AUTHENTICATION_REQUIRED,
    RESOURCE_NOT_FOUND,
    TOKEN_EXPIRED_OR_INVALID,
    DELETED_DISH_IN_ORDER,
    INCORRECT_DATA_FOR_DISH,
    INCORRECT_DATA_FOR_ADDON,
    INVALID_TOTAL_PRICE,
    USER_ALREADY_EXIST,
    INCORRECT_EMAIL_OR_PASSWORD,
    ADDRESS_REQUIRED,
    ENTITY_ALREADY_EXISTS,

    OUT_OF_BOUNDS,
    MUST_NOT_BE_EMPTY,
    INVALID_EMAIL,

    UNDEFINED;
}
