package ru.bironix.super_food.dtos.responses;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ErrorResponse {

    @JsonInclude(value = JsonInclude.Include.NON_NULL)
    String fieldName;

    String message;
}
