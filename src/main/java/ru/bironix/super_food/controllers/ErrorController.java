package ru.bironix.super_food.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import ru.bironix.super_food.converters.Converter;
import ru.bironix.super_food.dtos.responses.ErrorResponse;
import ru.bironix.super_food.exceptions.*;

import javax.validation.ConstraintViolationException;

@ControllerAdvice
public class ErrorController {

    @Autowired
    Converter con;

    @ResponseBody
    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse onConstraintValidationException(ConstraintViolationException e) {
        return e.getConstraintViolations()
                .stream()
                .map(violation -> ErrorResponse.builder()
                        .fieldName(violation.getPropertyPath().toString())
                        .message(violation.getMessage())
                        .build()
                ).findAny().get();
    }


    @ResponseBody
    @ExceptionHandler(NotFoundSourceException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse onConstraintValidationException(NotFoundSourceException e) {
        return ErrorResponse.builder()
                .message(e.getMessage())
                .entityName(e.getEntityName())
                .elements(e.getNotFoundEntities())
                .build();
    }

    @ResponseBody
    @ExceptionHandler(InvalidTotalPriceException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse onInvalidTotalPriceException(InvalidTotalPriceException e) {
        return ErrorResponse.builder()
                .message(e.getMessage())
                .build();
    }

    @ResponseBody
    @ExceptionHandler(DeletedDishInOrderException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse onDeletedDishInOrderException(DeletedDishInOrderException e) {
        return ErrorResponse.builder()
                .message(e.getMessage())
                .ids(e.getDeletedIds())
                .build();
    }

    @ResponseBody
    @ExceptionHandler(InvalidDishInOrderException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse onInvalidDishInOrderException(InvalidDishInOrderException e) {
        return ErrorResponse.builder()
                .message(e.getMessage())
                .elements(con.toDishesDto(e.getInvalidDishes()))
                .build();
    }


    @ResponseBody
    @ExceptionHandler(JwtAuthenticationException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ErrorResponse onJwtAuthenticationException(JwtAuthenticationException e) {
        return ErrorResponse.builder()
                .message(e.getMessage())
                .build();
    }


    @ResponseBody
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ErrorResponse onMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        return ErrorResponse.builder()
                .message(e.getBindingResult().getFieldError().getDefaultMessage())
                .fieldName(e.getBindingResult().getFieldError().getField())
                .build();
    }
}
