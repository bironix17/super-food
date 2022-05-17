package ru.bironix.super_food;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import ru.bironix.super_food.converters.Converter;
import ru.bironix.super_food.dtos.action.ActionDto;
import ru.bironix.super_food.dtos.dish.DishDto;
import ru.bironix.super_food.store.db.models.dish.Dish;
import ru.bironix.super_food.store.db.models.dish.Portion;
import ru.bironix.super_food.support.*;

import javax.transaction.Transactional;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import static java.util.stream.Collectors.toList;
import static org.hamcrest.Matchers.containsString;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
@DisplayName("Акция")
public class ActionTest extends AbstractTest {

    @Autowired
    public ActionTest(Services services,
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
    @DisplayName("Создать акцию")
    void createActionTest() throws Exception {
        var admin = getRegisteredAdmin();
        var action = mock.getAction();
        var dishes = new ArrayList<>(List.of(getSavedDish(), getSavedDish()));
        var newPrice = 200;
        action.setDishes(dishes);
        action.setPortions(dishes.stream()
                .map(Dish::getBasePortion)
                .peek(p -> p.setPriceNow(mock.getPrice(newPrice)))
                .collect(toList()));

        var jsonResponse = this.mockMvc.perform(
                        sendObjectDto(
                                con.toCreteUpdateActionDto(action),
                                addAuth(admin, post("/admin/actions"))
                        ))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("id")))
                .andReturn().getResponse().getContentAsString(StandardCharsets.UTF_8);

        var actionResponse = mapper.readValue(jsonResponse, ActionDto.Base.Full.class);
        var actionDb = daos.actionDao.findById(actionResponse.getId());
        assertTrue(actionDb.isPresent());
        var portionsIds = dishes.stream()
                .map(Dish::getBasePortion)
                .map(Portion::getId)
                .collect(toList());
        var dishesIds = dishes.stream()
                .map(Dish::getId)
                .collect(toList());

        assertTrue(actionDb.get().getDishes().stream() // Все блюда сохранились верно
                .allMatch(d -> dishesIds.contains(d.getId())));

        assertTrue(portionsIds.stream() // Все порции сохранились верно
                .allMatch(p -> actionDb.get().getPortions().stream()
                        .map(Portion::getId)
                        .anyMatch(id -> id == p))
        );
        assertTrue(actionDb.get().getPortions().stream() // Для всех блюд цена правильно изменилась
                .allMatch(p -> p.getPriceNow().getPrice() == newPrice));
    }


    @Test
    @Transactional
    @DisplayName("Получить изменённое блюдо после создания акции")
    void createActionAndGetDishTest() throws Exception {
        var client = getRegisteredClient();
        var action = getSavedAction();

        var jsonResponse = this.mockMvc.perform(
                        addAuth(client, get(String.format("/client/dishes/%s", action.getDishes().get(0).getId())))
                )
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("id")))
                .andReturn().getResponse().getContentAsString(StandardCharsets.UTF_8);

        var dishResponse = mapper.readValue(jsonResponse, DishDto.Base.Full.class);

        assertTrue(dishResponse.getBasePortion().getPriceNow().getPrice().equals( // Цена подставилась верно
                        action.getDishes().get(0).getBasePortion().getPriceNow().getPrice()
                )
        );
    }


    @Test
    @Transactional
    @DisplayName("Удалить акцию")
    void deleteActionTest() throws Exception {
        var admin = getRegisteredAdmin();
        var action = getSavedAction();
        var oldPrice = action.getPortions().get(0).getOldPrice().getPrice();

        this.mockMvc.perform(
                        sendObjectDto(
                                con.toCreteUpdateActionDto(action),
                                addAuth(admin, delete(String.format("/admin/actions/%s", action.getId())))
                        ))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("true")));

        var dishDb = daos.dishDao.findById(action.getDishes().get(0).getId());
        assertTrue(dishDb.isPresent());
        assertTrue(dishDb.get().getBasePortion().getPriceNow().getPrice().equals(oldPrice));
    }

}