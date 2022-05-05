package ru.bironix.super_food.exceptions;

import org.springframework.security.core.AuthenticationException;
import ru.bironix.super_food.constants.ApiError;

public class UserAlreadyExistAuthenticationException extends AuthenticationException {

    public UserAlreadyExistAuthenticationException() {
        super(ApiError.USER_ALREADY_EXIST.name());
    }

}