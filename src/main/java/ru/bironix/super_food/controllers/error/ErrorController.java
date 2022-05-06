package ru.bironix.super_food.controllers.error;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import ru.bironix.super_food.converters.Converter;
import ru.bironix.super_food.dtos.response.ErrorResponseDto;
import ru.bironix.super_food.exceptions.*;

import javax.validation.ConstraintViolationException;

@ControllerAdvice
@ResponseBody
public class ErrorController {

    private final Converter con;

    @Autowired
    public ErrorController(Converter con) {
        this.con = con;
    }

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponseDto onConstraintValidationException(ConstraintViolationException e) {
        return e.getConstraintViolations()
                .stream()
                .map(violation -> ErrorResponseDto.builder()
                        .fieldName(violation.getPropertyPath().toString())
                        .message(violation.getMessage())
                        .build()
                ).findAny().orElseThrow();
    }


    @ExceptionHandler(NotFoundSourceException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponseDto onNotFoundSourceException(NotFoundSourceException e) {
        return ErrorResponseDto.builder()
                .message(e.getMessage())
                .entityName(e.getEntityName())
                .ids(e.getNotFoundIds())
                .build();
    }


    @ExceptionHandler(InvalidTotalPriceException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponseDto onInvalidTotalPriceException(InvalidTotalPriceException e) {
        return ErrorResponseDto.builder()
                .message(e.getMessage())
                .build();
    }


    @ExceptionHandler(DeletedDishInOrderException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponseDto onDeletedDishInOrderException(DeletedDishInOrderException e) {
        return ErrorResponseDto.builder()
                .message(e.getMessage())
                .ids(e.getDeletedIds())
                .build();
    }


    @ExceptionHandler(InvalidDishInOrderException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponseDto onInvalidDishInOrderException(InvalidDishInOrderException e) {
        return ErrorResponseDto.builder()
                .message(e.getMessage())
                .elements(con.toDishesDto(e.getInvalidDishes()))
                .build();
    }


    @ExceptionHandler(JwtAuthenticationException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ErrorResponseDto onJwtAuthenticationException(JwtAuthenticationException e) {
        return ErrorResponseDto.builder()
                .message(e.getMessage())
                .build();
    }


    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponseDto onMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        return ErrorResponseDto.builder()
                .message(e.getBindingResult().getFieldError().getDefaultMessage())
                .fieldName(e.getBindingResult().getFieldError().getField())
                .build();
    }

    @ExceptionHandler(UserAlreadyExistAuthenticationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponseDto onUserAlreadyExistAuthenticationException(UserAlreadyExistAuthenticationException e) {
        return ErrorResponseDto.builder()
                .message(e.getMessage())
                .build();
    }
}
