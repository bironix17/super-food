package ru.bironix.super_food.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;
import ru.bironix.super_food.converters.Converter;
import ru.bironix.super_food.db.models.person.Person;
import ru.bironix.super_food.dtos.AuthRequestDto;
import ru.bironix.super_food.dtos.responses.AuthResponseDto;
import ru.bironix.super_food.security.JwtTokenProvider;
import ru.bironix.super_food.services.PersonService;

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
    @ResponseBody
    AuthResponseDto register(@RequestBody
                             @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Сущность авторизации")
                             AuthRequestDto request) {


        var person = service.createPerson(con.toPerson(request));
        String token = jwtTokenProvider.createToken(person.getEmail(), person.getRole());

        return new AuthResponseDto(person.getId(), token);
    }


    @Operation(summary = "Войти")
    @PostMapping("/login")
    @ResponseBody
    AuthResponseDto login(@RequestBody
                          @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Сущность авторизации")
                          AuthRequestDto request) {

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));

        Person person = service.getPersonByEmail(request.getEmail())
                .orElseThrow(() -> new UsernameNotFoundException("User with email: "
                        + request.getEmail()
                        + " not found"));

        String token = jwtTokenProvider.createToken(person.getEmail(), person.getRole());

        return new AuthResponseDto(person.getId(), token);
    }


}
