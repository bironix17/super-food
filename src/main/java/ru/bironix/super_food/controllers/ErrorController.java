package ru.bironix.super_food.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import ru.bironix.super_food.constants.ApiError;
import ru.bironix.super_food.dtos.response.ErrorResponseDto;
import ru.bironix.super_food.exceptions.*;

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

        ApiError apiError = resolveApiError(
                e.getBindingResult().getFieldError().getCode()
                        + e.getBindingResult().getFieldError().getField());

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

        } else if (messageTemplate.contains("Pattern") &&
                messageTemplate.contains("phoneNumber")) {
            return ApiError.INVALID_PHONE_NUMBER;
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


    @ExceptionHandler(DeletedDishesInOrderException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponseDto onDeletedDishInOrderException(DeletedDishesInOrderException e) {
        return ErrorResponseDto.builder()
                .errorCode(e.getApiError())
                .message(e.getMessage())
                .ids(e.getDeletedIds())
                .build();
    }

    @ExceptionHandler(DeletedAddonsInOrderException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponseDto onDeletedAddonInOrderException(DeletedAddonsInOrderException e) {
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
