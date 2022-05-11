package ru.bironix.super_food.oldVersion;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.MockMvc;
import ru.bironix.super_food.dtos.person.AddressDto;
import ru.bironix.super_food.dtos.person.PersonDto;
import ru.bironix.super_food.services.PersonService;
import ru.bironix.super_food.store.db.dao.person.AddressDao;
import ru.bironix.super_food.store.db.dao.person.PersonDao;
import ru.bironix.super_food.store.db.models.person.Address;
import ru.bironix.super_food.store.db.models.person.Person;
import ru.bironix.super_food.oldVersion.utils.CreateUtils;

import javax.transaction.Transactional;

import static org.hamcrest.Matchers.containsString;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Rollback(value = false)
@DisplayName("Пользователь")
public class PersonTest {

    @Autowired
    PersonService personService;

    @Autowired
    PersonDao personDao;

    @Autowired
    AddressDao addressDao;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    CreateUtils cu;

    Person person;
    Address address;

//    @BeforeAll
//    void fillData() {
//        var person = personService.createPerson(cu.getPerson());
//        personService.addAddressForPerson(person.getId(), "Садовая");
//    }


    @Test
    @Order(1)
    @DisplayName("Создание пользователя")
    void createPeron() throws Exception {
        var jsonResponse = this.mockMvc.perform(post("/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(cu.mapper.writeValueAsString(cu.con.toDto(cu.getPerson())))
                )
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("id")))
                .andReturn().getResponse().getContentAsString();

        person = cu.con.fromDto(cu.mapper.readValue(jsonResponse, PersonDto.Create.class));
        assertTrue(personDao.existsById(person.getId()));
    }


    @Test
    @Order(2)
    @DisplayName("запросить информацию о пользователе")
    void getPerson() throws Exception {
        this.mockMvc.perform(get("/getMe/{id}", person.getId()))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("id")));
    }

    @Test
    @Order(3)
    @DisplayName("Изменить инфу о пользователе")
    void updatePerson() throws Exception {
        var updateData = Person.builder()
                .id(person.getId())
                .name("Федя")
                .build();

        var jsonResponse = this.mockMvc.perform(put("/updateMe")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(cu.mapper.writeValueAsString(cu.con.toDto(updateData))))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("id")))
                .andReturn().getResponse().getContentAsString();

        var updatedPerson = cu.con.fromDto(cu.mapper.readValue(jsonResponse, PersonDto.Update.class));
        assertEquals(updateData.getName(), updatedPerson.getName());
        assertEquals(updateData.getName(), personDao.findById(person.getId()).get().getName());
    }

    @Test
    @Order(4)
    @DisplayName("Добавить адрес пользователю")
    void addAddress() throws Exception {
        var newAddressValue = "Садовая";

        var jsonResponse = this.mockMvc.perform(put("/addAddress/{id}", person.getId())
                        .param("address", newAddressValue))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("id")))
                .andReturn().getResponse().getContentAsString();

        var address = cu.con.fromDto(cu.mapper.readValue(jsonResponse, AddressDto.class));

        assertEquals(newAddressValue, address.getAddress());
        assertTrue(addressDao.existsById(address.getId()));
    }


    @Test
    @Order(5)
    @DisplayName("Удалить адрес у пользователя")
    void deleteAddress() throws Exception {
        this.mockMvc.perform(put("/deleteAddress/{id}", address.getId()))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("true")));

        assertFalse(addressDao.existsById(address.getId()));
    }

}