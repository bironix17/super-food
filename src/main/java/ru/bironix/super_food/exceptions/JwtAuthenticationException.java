package ru.bironix.super_food.exceptions;

import org.springframework.security.core.AuthenticationException;

public class JwtAuthenticationException extends AuthenticationException {

    public JwtAuthenticationException() {
        super("Ошибка. Токен истёк или не валиден");
    }

}
