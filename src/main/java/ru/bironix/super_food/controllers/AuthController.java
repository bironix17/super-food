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
import ru.bironix.super_food.dtos.TokenDto;
import ru.bironix.super_food.dtos.response.TokensDto;
import ru.bironix.super_food.exceptions.NotFoundSourceException;
import ru.bironix.super_food.security.log.SecurityLogger;
import ru.bironix.super_food.services.RefreshTokenService;
import ru.bironix.super_food.store.db.models.person.Person;
import ru.bironix.super_food.dtos.response.AuthResponseDto;
import ru.bironix.super_food.security.jwt.JwtTokenProvider;
import ru.bironix.super_food.services.PersonService;
import ru.bironix.super_food.store.db.models.person.RefreshToken;

import javax.validation.Valid;

@Tag(name = "Авторизация")
@RestController
@RequestMapping("/auth")
public class AuthController {

    private final PersonService service;
    private final RefreshTokenService refreshTokenService;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final Converter con;
    private final SecurityLogger securityLogger;

    @Autowired
    public AuthController(PersonService service,
                          RefreshTokenService refreshTokenService,
                          AuthenticationManager authenticationManager,
                          SecurityLogger securityLogger,
                          JwtTokenProvider jwtTokenProvider,
                          Converter con) {
        this.service = service;
        this.refreshTokenService = refreshTokenService;
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
        this.con = con;
        this.securityLogger = securityLogger;
    }

    @Operation(summary = "Зарегистрироваться")
    @PostMapping("/register")
    public AuthResponseDto register(@RequestBody
                                    @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Сущность авторизации")
                                    @Valid AuthRequestDto request) {

        securityLogger.attemptRegistryPerson(request);
        var person = service.createPerson(con.toPerson(request));
        securityLogger.registryPerson(person);
        return generateAuthResponse(person);
    }


    @Operation(summary = "Войти")
    @PostMapping("/login")
    public AuthResponseDto login(@RequestBody
                                 @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Сущность авторизации")
                                 @Valid AuthRequestDto request) {

        securityLogger.attemptLoginPerson(request);

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));

        Person person = service.getPersonByEmail(request.getEmail())
                .orElseThrow(() -> new UsernameNotFoundException(ApiError.INCORRECT_EMAIL_OR_PASSWORD.name()));

        securityLogger.loginPerson(person);
        return generateAuthResponse(person);
    }


    @Operation(summary = "Обновить access token")
    @PostMapping("/refreshToken")
    public TokensDto updateAccessToken(@RequestBody
                                       @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "refresh token")
                                       @Valid TokenDto tokenDto) {

        return refreshTokenService.findByToken(tokenDto.getToken())
                .map(refreshTokenService::verifyExpiration)
                .map(RefreshToken::getPerson)
                .map(this::generateUpdateTokensResponse)
                .orElseThrow(() -> new NotFoundSourceException("refresh token"));
    }

    private AuthResponseDto generateAuthResponse(Person person) {
        String accessToken = jwtTokenProvider.createAccessToken(person);
        RefreshToken refreshToken = refreshTokenService.create(person);

        return new AuthResponseDto(person.getId(), accessToken, refreshToken.getToken());
    }

    private TokensDto generateUpdateTokensResponse(Person person) {
        String accessToken = jwtTokenProvider.createAccessToken(person);
        RefreshToken refreshToken = refreshTokenService.create(person);

        return new TokensDto(accessToken, refreshToken.getToken());
    }
}
