package ru.bironix.super_food;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.bironix.super_food.constants.ApiError;
import ru.bironix.super_food.converters.Converter;
import ru.bironix.super_food.dtos.AuthRequestDto;
import ru.bironix.super_food.dtos.TokenDto;
import ru.bironix.super_food.dtos.response.AuthResponseDto;
import ru.bironix.super_food.support.*;

import javax.transaction.Transactional;

import java.nio.charset.StandardCharsets;

import static org.hamcrest.Matchers.containsString;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
@DisplayName("Авторизация")
public class AuthTest extends AbstractTest {

    @Autowired
    public AuthTest(Services services,
                    Daos daos,
                    Controllers controllers,
                    Utils utils,
                    Mock mock,
                    Converter con,
                    MockMvc mockMvc,
                    ObjectMapper mapper) {
        super(services, daos, controllers, utils, mock, con, mockMvc, mapper);
    }

    @Test
    @Transactional
    @DisplayName("Регистрация")
    void registrationTest() throws Exception {
        var client = mock.getPersonClient();
        var jsonResponse = this.mockMvc.perform(
                        sendObjectDto(client,
                                post("/auth/register"))
                )
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("personId")))
                .andReturn().getResponse().getContentAsString(StandardCharsets.UTF_8);

        var authResponse = mapper.readValue(jsonResponse, AuthResponseDto.class);
        assertTrue(daos.personDao.existsById(authResponse.getPersonId()));
    }


    @Test
    @Transactional
    @DisplayName("Вход")
    void loginTest() throws Exception {
        var client = getRegisteredClient();
        var jsonResponse = this.mockMvc.perform(
                        sendObjectDto(client,
                                post("/auth/login"))
                )
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("personId")))
                .andReturn().getResponse().getContentAsString(StandardCharsets.UTF_8);

        var authResponse = mapper.readValue(jsonResponse, AuthResponseDto.class);
        assertTrue(daos.personDao.existsById(authResponse.getPersonId()));
    }

    @Test
    @Transactional
    @DisplayName("Обновление токена")
    void refreshTokenTest() throws Exception {
        var client = getRegisteredClient();
        var refreshToken = controllers.authController.login(
                AuthRequestDto.builder()
                        .email(client.getEmail())
                        .password(client.getPassword())
                        .build()
        ).getRefreshToken();

        this.mockMvc.perform(
                        sendObjectDto(new TokenDto(refreshToken),
                                post("/auth/refreshToken")
                        ))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("accessToken")));
    }


    @Test
    @Transactional
    @DisplayName("Получить отказ в доступе к ресурсу без авторизации")
    void authExceptionTest() throws Exception {
        var client = mock.getPersonClient();
        this.mockMvc.perform(get("/client/my")
                )
                .andExpect(status().isUnauthorized())
                .andExpect(content().string(containsString(ApiError.AUTHENTICATION_REQUIRED.name())));
    }

    @Test
    @Transactional
    @DisplayName("Получить отказ в доступе к ресурсу админа клиенту")
    void getAdminResourceWithClientExceptionTest() throws Exception {
        var client = getRegisteredClient();
        this.mockMvc.perform(addAuth(client, get("/admin/persons/1")
                ))
                .andExpect(status().isForbidden())
                .andExpect(content().string(containsString(ApiError.RESOURCE_ACCESS_DENIED.name())));
    }

    @Test
    @Transactional
    @DisplayName("Получить доступ к ресурсу админа админу")
    void getAdminResourceTest() throws Exception {
        var client = getRegisteredClient();
        var admin = getRegisteredAdmin();
        this.mockMvc.perform(addAuth(admin, get(String.format("/admin/persons/%s", client.getId()))
                ))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("id")));
    }
}