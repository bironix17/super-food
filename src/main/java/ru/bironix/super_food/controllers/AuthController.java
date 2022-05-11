package ru.bironix.super_food.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.bironix.super_food.constants.ApiError;
import ru.bironix.super_food.converters.Converter;
import ru.bironix.super_food.dtos.AuthRequestDto;
import ru.bironix.super_food.store.db.models.person.Person;
import ru.bironix.super_food.dtos.response.AuthResponseDto;
import ru.bironix.super_food.security.JwtTokenProvider;
import ru.bironix.super_food.services.PersonService;

import javax.validation.Valid;

@Tag(name = "Авторизация")
@RestController
@RequestMapping("/auth")
public class AuthController {

    private final PersonService service;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final Converter con;

    @Autowired
    public AuthController(PersonService service, AuthenticationManager authenticationManager,
                          JwtTokenProvider jwtTokenProvider, Converter con) {
        this.service = service;
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
        this.con = con;
    }

    @Operation(summary = "Зарегистрироваться")
    @PostMapping("/register")
    public AuthResponseDto register(@RequestBody
                             @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Сущность авторизации")
                             @Valid AuthRequestDto request) {


        var person = service.createPerson(con.toPerson(request));
        String token = jwtTokenProvider.createToken(person.getEmail(), person);

        return new AuthResponseDto(person.getId(), token);
    }


    @Operation(summary = "Войти")
    @PostMapping("/login")
    public AuthResponseDto login(@RequestBody
                          @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Сущность авторизации")
                          @Valid AuthRequestDto request) {

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));

        Person person = service.getPersonByEmail(request.getEmail())
                .orElseThrow(() -> new UsernameNotFoundException(ApiError.INCORRECT_EMAIL_OR_PASSWORD.name()));

        String token = jwtTokenProvider.createToken(person.getEmail(), person);

        return new AuthResponseDto(person.getId(), token);
    }


}
