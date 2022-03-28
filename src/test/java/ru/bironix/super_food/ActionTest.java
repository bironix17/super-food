package ru.bironix.super_food;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import ru.bironix.super_food.controllers.ActionController;
import ru.bironix.super_food.controllers.DishController;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
@DisplayName("Акция")
public class ActionTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("Список блюд")
    void getDishes() throws Exception {
        this.mockMvc.perform(get("/actions")).andExpect(status().isOk())
                .andExpect(content().string(containsString("id")));
    }

    @Test
    @DisplayName("Конкретное блюдо")
    void getDish() throws Exception {
        this.mockMvc.perform(get("/action/{id}", 0)).andExpect(status().isOk())
                .andExpect(content().string(containsString("id")));
    }
}
