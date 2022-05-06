package ru.bironix.super_food.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.MediaType;
import ru.bironix.super_food.dtos.response.ErrorResponseDto;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ErrorAuthResponse {

    public static void addError(HttpServletResponse response, String message) throws IOException {

        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);


        final var errorResponse = ErrorResponseDto.builder()
                .message(message)
                .build();

        final ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(response.getOutputStream(), errorResponse);
    }
}
