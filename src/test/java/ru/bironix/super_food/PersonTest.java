package ru.bironix.super_food;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.bironix.super_food.converters.Converter;
import ru.bironix.super_food.dtos.person.AddressDto;
import ru.bironix.super_food.dtos.person.PersonDto;
import ru.bironix.super_food.store.db.models.person.Person;
import ru.bironix.super_food.support.*;

import javax.transaction.Transactional;
import java.nio.charset.StandardCharsets;

import static org.hamcrest.Matchers.containsString;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
@DisplayName("Пользователь")
public class PersonTest extends AbstractTest {

    @Autowired
    public PersonTest(Services services,
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
    @DisplayName("Получение своего пользователя")
    void getPersonMyTest() throws Exception {
        var client = getRegisteredClient();
        this.mockMvc.perform(addAuth(client, get("/client/my"))
                )
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("id")));
    }


    @Test
    @Transactional
    @DisplayName("Изменение своего пользователя")
    void updatePersonTest() throws Exception {
        var client = getRegisteredClient();
        var name = "Vladimir";
        var phoneNumber = "89187777777";
        var updatableClient = new Person();
        updatableClient.setName(name);
        updatableClient.setPhoneNumber(phoneNumber);
        var jsonResponse = this.mockMvc.perform(addAuth(client, put("/client/my")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(utils.personToUpdateDto(updatableClient))))
                )
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("id")))
                .andReturn().getResponse().getContentAsString(StandardCharsets.UTF_8);
        var personResponse = mapper.readValue(jsonResponse, PersonDto.Base.class);
        assertTrue(personResponse.getName().equals(name));
        assertTrue(personResponse.getPhoneNumber().equals(phoneNumber));

        var personDb = daos.personDao.findById(personResponse.getId());
        assertTrue(personDb.isPresent());
        assertTrue(personDb.get().getName().equals(name));
        assertTrue(personDb.get().getPhoneNumber().equals(phoneNumber));
    }

    @Test
    @Transactional
    @DisplayName("Добавление адреса пользователю")
    void addAddressForPersonMyTest() throws Exception {
        var newAddress = "Садовая 31";
        var client = getRegisteredClient();
        var jsonResponse = this.mockMvc.perform(addAuth(client, post("/client/my/addresses")
                        .param("address", newAddress))
                )
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("id")))
                .andReturn().getResponse().getContentAsString(StandardCharsets.UTF_8);

        var addressDto = mapper.readValue(jsonResponse, AddressDto.class);
        var createdAddress = daos.addressDao.findById(addressDto.getId());
        assertTrue(createdAddress.isPresent());
        assertTrue(createdAddress.get().getAddress().equals(newAddress));
    }

    @Test
    @Transactional
    @DisplayName("Удаление адреса у пользователя")
    void deleteAddressForPersonMyTest() throws Exception {
        var client = getRegisteredClient();
        var addressValue = "Садовая 31";
        var newAddress = services.personService.addAddressForPerson(client.getId(), addressValue);

        assertTrue(daos.addressDao.findById(newAddress.getId()).isPresent());
        assertTrue(daos.addressDao.findById(newAddress.getId()).get().getAddress().equals(addressValue));

        this.mockMvc.perform(
                        addAuth(client, delete(String.format("/client/my/addresses/%s", newAddress.getId())))
                )
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("true")));


        assertFalse(daos.addressDao.findById(newAddress.getId()).isPresent());
    }
}