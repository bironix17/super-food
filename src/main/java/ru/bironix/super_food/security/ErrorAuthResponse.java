package ru.bironix.super_food.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.MediaType;
import ru.bironix.super_food.constants.ApiError;
import ru.bironix.super_food.dtos.response.ErrorResponseDto;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ErrorAuthResponse {

    public static void addError(HttpServletResponse response, ApiError apiError) throws IOException {
        addError(response, apiError, HttpServletResponse.SC_UNAUTHORIZED);
    }

    public static void addError(HttpServletResponse response, ApiError apiError, int status) throws IOException {

        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(status);


        final var errorResponse = ErrorResponseDto.builder()
                .errorCode(apiError)
                .message(apiError.name())
                .build();

        final ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(response.getOutputStream(), errorResponse);
    }
}
