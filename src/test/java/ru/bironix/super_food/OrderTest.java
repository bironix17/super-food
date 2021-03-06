package ru.bironix.super_food;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import ru.bironix.super_food.converters.Converter;
import ru.bironix.super_food.dtos.order.OrderDto;
import ru.bironix.super_food.dtos.order.OrderStatusDto;
import ru.bironix.super_food.dtos.response.PageOrdersWithTotalCountDto;
import ru.bironix.super_food.store.db.models.dish.OrderedDish;
import ru.bironix.super_food.support.*;

import javax.transaction.Transactional;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Objects;

import static org.hamcrest.Matchers.containsString;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@DisplayName("Заказ")
public class OrderTest extends AbstractTest {

    @Autowired
    public OrderTest(Services services,
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
    @DisplayName("Создание заказа")
    void createOrderTest() throws Exception {
        var client = getRegisteredClient();
        var dish = getSavedDish();
        var order = mock.getOrder();
        order.setClient(client);
        order.setDishes(List.of(OrderedDish.builder()
                .dishPrice(dish.getBasePortion().getPriceNow())
                .id(null)
                .dish(dish)
                .portion(dish.getBasePortion())
                .count(1)
                .orderedAddons(null)
                .build()));


        var jsonResponse = this.mockMvc.perform(sendObjectDto(
                        con.toCreateUpdateOrderDto(order),
                        addAuth(client, post("/client/my/orders"))
                ))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("id")))
                .andReturn().getResponse().getContentAsString(StandardCharsets.UTF_8);

        var orderResponse = mapper.readValue(jsonResponse, OrderDto.Base.Full.class);
        var orderDb = daos.orderDao.findById(orderResponse.getId());
        assertTrue(orderDb.isPresent());
    }

    @Test
    @Transactional
    @DisplayName("Получение заказа")
    void getOrderTest() throws Exception {
        var client = getRegisteredClient();
        var order = getSavedOrder(client);

        var jsonResponse = this.mockMvc.perform(
                        addAuth(client, get(String.format("/client/my/orders/%s", order.getId())))
                )
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("id")))
                .andReturn().getResponse().getContentAsString(StandardCharsets.UTF_8);

        var orderResponse = mapper.readValue(jsonResponse, OrderDto.Base.Full.class);
        assertTrue(Objects.equals(orderResponse.getId(), order.getId()));
    }


    @Test
    @Transactional
    @DisplayName("Получение истории заказов")
    void getOrdersTest() throws Exception {
        var client = getRegisteredClient();
        var order = getSavedOrder(client);

        var jsonResponse = this.mockMvc.perform(
                        addAuth(client, get("/client/my/orders"))
                )
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("id")))
                .andReturn().getResponse().getContentAsString(StandardCharsets.UTF_8);

        var ordersResponse = mapper.readValue(jsonResponse, PageOrdersWithTotalCountDto.class);
        assertTrue(ordersResponse.getOrders().stream()
                .anyMatch(o -> o.getId() == order.getId()));
    }

    @Test
    @Transactional
    @DisplayName("Изменение статуса заказа поваром")
    void changeOrderStatusByCookTest() throws Exception {
        var cook = getRegisteredCook();
        var client = getRegisteredClient();
        var order = getSavedOrder(client);
        var newStatus = OrderStatusDto.COOKED;

        var jsonResponse = this.mockMvc.perform(
                        addAuth(cook, put(String.format("/cook/orders/%s/status/%s", order.getId(), newStatus)))
                )
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("id")))
                .andReturn().getResponse().getContentAsString(StandardCharsets.UTF_8);

        var orderResponse = mapper.readValue(jsonResponse, OrderDto.Base.Full.class);
        assertTrue(orderResponse.getStatus().equals(newStatus));

        var orderDb = daos.orderDao.findById(orderResponse.getId());
        assertTrue(orderDb.isPresent());
        assertTrue(orderDb.get().getStatus().equals(con.fromDto(newStatus)));
    }

    @Test
    @Transactional
    @DisplayName("Изменение статуса заказа доставщиком")
    void changeOrderStatusByDeliverymanTest() throws Exception {
        var deliveryman = getRegisteredDeliveryman();
        var client = getRegisteredClient();
        var order = getSavedOrder(client);
        var newStatus = OrderStatusDto.COMPLETED;

        var jsonResponse = this.mockMvc.perform(
                        addAuth(deliveryman, put(String.format("/deliveryman/orders/%s/status/%s", order.getId(), newStatus)))
                )
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("id")))
                .andReturn().getResponse().getContentAsString(StandardCharsets.UTF_8);

        var orderResponse = mapper.readValue(jsonResponse, OrderDto.Base.Full.class);
        assertTrue(orderResponse.getStatus().equals(newStatus));

        var orderDb = daos.orderDao.findById(orderResponse.getId());
        assertTrue(orderDb.isPresent());
        assertTrue(orderDb.get().getStatus().equals(con.fromDto(newStatus)));
    }


    @Test
    @Transactional
    @DisplayName("Изменение статуса заказа менеджером")
    void changeOrderStatusByManagerTest() throws Exception {
        var manager = getRegisteredManager();
        var client = getRegisteredClient();
        var order = getSavedOrder(client);
        var newStatus = OrderStatusDto.COOK;

        var jsonResponse = this.mockMvc.perform(
                        addAuth(manager, put(String.format("/manager/orders/%s/status/%s", order.getId(), newStatus)))
                )
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("id")))
                .andReturn().getResponse().getContentAsString(StandardCharsets.UTF_8);

        var orderResponse = mapper.readValue(jsonResponse, OrderDto.Base.Full.class);
        assertTrue(orderResponse.getStatus().equals(newStatus));

        var orderDb = daos.orderDao.findById(orderResponse.getId());
        assertTrue(orderDb.isPresent());
        assertTrue(orderDb.get().getStatus().equals(con.fromDto(newStatus)));
    }

}