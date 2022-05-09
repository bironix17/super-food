package ru.bironix.super_food.controllers.error;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import ru.bironix.super_food.constants.ApiError;
import ru.bironix.super_food.dtos.response.ErrorResponseDto;
import ru.bironix.super_food.exceptions.ApiException;
import ru.bironix.super_food.exceptions.DeletedDishInOrderException;
import ru.bironix.super_food.exceptions.InvalidEntitiesOrderException;
import ru.bironix.super_food.exceptions.NotFoundSourceException;

import javax.validation.ConstraintViolationException;

@ControllerAdvice
@ResponseBody
public class ErrorController {

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponseDto onConstraintValidationException(ConstraintViolationException e) {
        var error = e.getConstraintViolations().iterator().next();
        ApiError apiError = resolveApiError(error.getMessageTemplate());

        return ErrorResponseDto.builder()
                .fieldName(error.getPropertyPath().toString())
                .errorCode(apiError)
                .message(error.getMessage())
                .build();
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponseDto onMethodArgumentNotValidException(MethodArgumentNotValidException e) {

        ApiError apiError = resolveApiError(e.getBindingResult().getFieldError().getCode());

        return ErrorResponseDto.builder()
                .errorCode(apiError)
                .message(e.getBindingResult().getFieldError().getDefaultMessage())
                .fieldName(e.getBindingResult().getFieldError().getField())
                .build();
    }


    private ApiError resolveApiError(String messageTemplate) {

        if (messageTemplate == null)
            return ApiError.UNDEFINED;

        if (messageTemplate.contains("Min")
                || messageTemplate.contains("Max")) {
            return ApiError.OUT_OF_BOUNDS;

        } else if (messageTemplate.contains("NotBlank")
                || messageTemplate.contains("NotEmpty")
                || messageTemplate.contains("NotNull")) {
            return ApiError.MUST_NOT_BE_EMPTY;
        } else if (messageTemplate.contains("Email")) {
            return ApiError.INVALID_EMAIL;
        } else {
            return ApiError.UNDEFINED;
        }
    }


    @ExceptionHandler(NotFoundSourceException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponseDto onNotFoundSourceException(NotFoundSourceException e) {
        return ErrorResponseDto.builder()
                .errorCode(e.getApiError())
                .message(e.getMessage())
                .entityName(e.getEntityName())
                .ids(e.getNotFoundIds())
                .build();
    }


    @ExceptionHandler(DeletedDishInOrderException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponseDto onDeletedDishInOrderException(DeletedDishInOrderException e) {
        return ErrorResponseDto.builder()
                .errorCode(e.getApiError())
                .message(e.getMessage())
                .ids(e.getDeletedIds())
                .build();
    }


    @ExceptionHandler(InvalidEntitiesOrderException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponseDto onInvalidDishInOrderException(InvalidEntitiesOrderException e) {
        return ErrorResponseDto.builder()
                .errorCode(e.getApiError())
                .message(e.getMessage())
                .ids(e.getInvalidEntitiesIds())
                .build();
    }

    @ExceptionHandler(ApiException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponseDto onApiException(ApiException e) {
        return ErrorResponseDto.builder()
                .errorCode(e.getApiError())
                .message(e.getMessage())
                .build();
    }


}
