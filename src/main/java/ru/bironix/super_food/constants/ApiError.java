package ru.bironix.super_food.constants;

import io.swagger.annotations.ApiModel;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(enumAsRef = true, name = "Сообщения об ошибках")
@ApiModel
public enum ApiError {
    AUTHENTICATION_REQUIRED,
    RESOURCE_ACCESS_DENIED,
    RESOURCE_NOT_FOUND,
    ACCESS_TOKEN_EXPIRED_OR_INVALID,
    DELETED_DISH_IN_ORDER,
    DELETED_ADDON_IN_ORDER,
    DELETED_DISH_IN_DISH,
    INCORRECT_DATA_FOR_DISH,
    INVALID_TOTAL_PRICE,
    USER_ALREADY_EXIST,
    USER_IS_BANNED,
    INCORRECT_EMAIL_OR_PASSWORD,
    ADDRESS_REQUIRED,
    ENTITY_ALREADY_EXISTS,
    ONLY_COMBO_CAN_CONTAIN_DISHES,
    EMPTY_DISHES_IN_COMBO,
    OUT_OF_BOUNDS,
    MUST_NOT_BE_EMPTY,
    INVALID_EMAIL,
    UNDEFINED,
    REFRESH_TOKEN_IS_EXPIRED,
    ALLOWED_NUMBER_OF_LOGIN_ATTEMPTS_EXCEEDED,
    DISH_IS_ALREADY_IN_THE_ACTION;
}
