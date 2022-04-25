package ru.bironix.super_food.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import ru.bironix.super_food.dtos.responses.ErrorResponse;
import ru.bironix.super_food.exceptions.NotFoundSource;

import javax.validation.ConstraintViolationException;

@ControllerAdvice
public class ErrorController {

    @ResponseBody
    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse onConstraintValidationException(ConstraintViolationException e) {
        return e.getConstraintViolations()
                .stream()
                .map(violation -> new ErrorResponse(
                                violation.getPropertyPath().toString(),
                                violation.getMessage()
                        )
                ).findAny().get();
    }


    @ResponseBody
    @ExceptionHandler(NotFoundSource.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse onConstraintValidationException(NotFoundSource e) {
        return ErrorResponse.builder()
                .message(e.getMessage())
                .build();
    }

}
