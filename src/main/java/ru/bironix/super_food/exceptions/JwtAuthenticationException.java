package ru.bironix.super_food.exceptions;

import org.springframework.security.core.AuthenticationException;
import ru.bironix.super_food.constants.ApiError;

public class JwtAuthenticationException extends AuthenticationException {

    public JwtAuthenticationException() {
        super(ApiError.TOKEN_EXPIRED_OR_INVALID.name());
    }

}
