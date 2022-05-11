package ru.bironix.super_food.oldVersion;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
@DisplayName("Блюдо")
public class DishTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("Список существующих блюд")
    void getExistingDishes() throws Exception {
        this.mockMvc.perform(get("/dishes")).andExpect(status().isOk())
                .andExpect(content().string(containsString("id")));
    }

    @Test
    @DisplayName("Конкретное существующее блюдо")
    void getExistingDish() throws Exception {
        this.mockMvc.perform(get("/dish/{id}", 0)).andExpect(status().isOk())
                .andExpect(content().string(containsString("id")));
    }

    @Test
    @DisplayName("Список блюд")
    void getDishes() throws Exception {
        this.mockMvc.perform(get("/dishes")).andExpect(status().isOk());
    }

    @Test
    @DisplayName("Конкретное  блюдо")
    void getDish() throws Exception {
        this.mockMvc.perform(get("/dish/{id}", 0)).andExpect(status().isOk());
    }
}
