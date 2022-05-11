package ru.bironix.super_food.newVersion;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.LinkedMultiValueMap;
import ru.bironix.super_food.converters.Converter;
import ru.bironix.super_food.dtos.dish.AddonDto;
import ru.bironix.super_food.dtos.dish.CategoryDto;
import ru.bironix.super_food.dtos.dish.DishDto;
import ru.bironix.super_food.dtos.response.ApiActionResponseDto;
import ru.bironix.super_food.newVersion.support.*;

import javax.transaction.Transactional;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.containsString;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
@DisplayName("Блюдо")
public class DishTest extends AbstractTest {

    @Autowired
    public DishTest(Services services,
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
    @DisplayName("Создание добавки")
    void createAddonTest() throws Exception {
        var admin = getRegisteredAdmin();
        var addon = mock.getAddon();
        var jsonResponse = this.mockMvc.perform(sendObjectDto(
                        con.toCreateUpdateAddonDto(addon),
                        addAuth(admin, post("/admin/addons"))
                ))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("id")))
                .andReturn().getResponse().getContentAsString(StandardCharsets.UTF_8);

        var addonResponse = mapper.readValue(jsonResponse, AddonDto.Base.class);
        var addonDb = daos.addonDao.findById(addonResponse.getId());
        assertTrue(addonDb.isPresent());
    }


    @Test
    @Transactional
    @DisplayName("Создание блюда")
    void createDishTest() throws Exception {
        var admin = getRegisteredAdmin();
        var addon = getSavedAddon();
        var dish = mock.getDish();
        dish.setAddons(new ArrayList<>(List.of(addon)));

        var jsonResponse = this.mockMvc.perform(
                        sendObjectDto(
                                con.toCreateUpdateDishDto(dish),
                                addAuth(admin, post("/admin/dishes"))
                        ))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("id")))
                .andReturn().getResponse().getContentAsString(StandardCharsets.UTF_8);

        var dishResponse = mapper.readValue(jsonResponse, DishDto.Base.Full.class);
        var dishDb = daos.dishDao.findById(dishResponse.getId());
        assertTrue(dishDb.isPresent());
        assertFalse(dishDb.get().getAddons().isEmpty());
    }

    @Test
    @Transactional
    @DisplayName("Получение списка блюд в категориях")
    void getDishesInCategoriesTest() throws Exception {
        var client = getRegisteredClient();
        var dish1 = getSavedDish();
        var dish2 = getSavedDish();
        var dish3 = getSavedDish();

        var jsonResponse = this.mockMvc.perform(
                        addAuth(client, get("/client/dishes/actual/categories"))
                )
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("id")))
                .andReturn().getResponse().getContentAsString(StandardCharsets.UTF_8);

        var categoriesResponse =
                Arrays.asList(mapper.readValue(jsonResponse, CategoryDto[].class));

        var dishesResponse = con.fromCategoriesDto(categoriesResponse);

        assertTrue(dishesResponse.size() >= 3);

        var createdDishesIds = List.of(dish1.getId(), dish2.getId(), dish3.getId());
        assertTrue(dishesResponse.stream()
                .filter(d -> createdDishesIds.contains(d.getId()))
                .count() == 3);
    }


    @Test
    @Transactional
    @DisplayName("Получение блюд из запрошенного списка")
    void getDishesByRequestListTest() throws Exception {
        var client = getRegisteredClient();
        var dish1 = getSavedDish();
        var dish2 = getSavedDish();

        var jsonResponse = this.mockMvc.perform(
                        addAuth(client, get("/client/specificDishes"))
                                .params(new LinkedMultiValueMap() {{
                                    add("ids", dish1.getId().toString());
                                    add("ids", dish2.getId().toString());
                                }})
                )
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("id")))
                .andReturn().getResponse().getContentAsString(StandardCharsets.UTF_8);

        var dishesResponse =
                Arrays.asList(mapper.readValue(jsonResponse, DishDto.Base.Small[].class));

        assertTrue(dishesResponse.size() == 2);

        var createdDishesIds = List.of(dish1.getId(), dish2.getId());
        assertTrue(dishesResponse.stream()
                .filter(d -> createdDishesIds.contains(d.getId()))
                .count() == 2);
    }

    @Test
    @Transactional
    @DisplayName("Удаление блюда")
    void deleteDishTest() throws Exception {
        var admin = getRegisteredAdmin();
        var dish = getSavedDish();

        var jsonResponse = this.mockMvc.perform(
                        addAuth(admin, delete(String.format("/admin/dishes/%s", dish.getId())))
                )
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("status")))
                .andReturn().getResponse().getContentAsString(StandardCharsets.UTF_8);

        var response =
                mapper.readValue(jsonResponse, ApiActionResponseDto.class);

        assertTrue(response.isStatus());
        var dishDb = daos.dishDao.findById(dish.getId());
        assertTrue(dishDb.isPresent());
        assertTrue(dishDb.get().getDeleted());

        var dishes = services.dishService.getActualDishes();
        assertTrue(dishes.stream()
                .noneMatch(d -> d.getId() == dish.getId())
        );
    }

    @Test
    @Transactional
    @DisplayName("Добавить порцию блюду")
    void addPortionForDishTest() throws Exception {
        var admin = getRegisteredAdmin();
        var dish = getSavedDish();
        var portion = mock.getPortion();
        portion.setSize("200 грамм");
        dish.getPortions().add(portion);

        var jsonResponse = this.mockMvc.perform(
                        sendObjectDto(
                                con.toCreateUpdateDishDto(dish),
                                addAuth(admin, put(String.format("/admin/dishes/%s", dish.getId())))
                        ))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("id")))
                .andReturn().getResponse().getContentAsString(StandardCharsets.UTF_8);

        var dishResponse = mapper.readValue(jsonResponse, DishDto.Base.Full.class);
        var dishDb = daos.dishDao.findById(dishResponse.getId());
        assertTrue(dishDb.isPresent());
        assertTrue(dishDb.get().getPortions().stream()
                .anyMatch(p -> p.getSize().equals(portion.getSize())));
    }

}