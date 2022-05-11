package ru.bironix.super_food.controllers.error;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;
import ru.bironix.super_food.constants.ApiError;
import ru.bironix.super_food.security.ErrorAuthResponse;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class AccessDeniedHandlerJwt implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest request,
                       HttpServletResponse response,
                       AccessDeniedException e) throws IOException, ServletException {

        ErrorAuthResponse.addError(response, ApiError.RESOURCE_ACCESS_DENIED, HttpServletResponse.SC_FORBIDDEN);
    }
}