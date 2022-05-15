package ru.bironix.super_food.security.error;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import ru.bironix.super_food.constants.ApiError;
import ru.bironix.super_food.security.ErrorAuthResponse;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class AuthenticationEntryPointJwt implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request,
                         HttpServletResponse response,
                         AuthenticationException e) throws IOException {

        if (e instanceof BadCredentialsException) {
            ErrorAuthResponse.addError(response, ApiError.INCORRECT_EMAIL_OR_PASSWORD);
        } else if (e instanceof DisabledException | e instanceof LockedException) {
            ErrorAuthResponse.addError(response, ApiError.USER_IS_BANNED);
        } else
            ErrorAuthResponse.addError(response, ApiError.AUTHENTICATION_REQUIRED);
    }
}