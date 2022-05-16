package ru.bironix.super_food.support;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import ru.bironix.super_food.converters.Converter;
import ru.bironix.super_food.store.db.models.action.Action;
import ru.bironix.super_food.store.db.models.dish.Addon;
import ru.bironix.super_food.store.db.models.dish.Dish;
import ru.bironix.super_food.store.db.models.dish.DishCount;
import ru.bironix.super_food.store.db.models.order.Order;
import ru.bironix.super_food.store.db.models.person.Person;

import java.nio.charset.StandardCharsets;
import java.util.List;

@SpringBootTest
@AutoConfigureMockMvc
public abstract class AbstractTest {

    public final Controllers controllers;
    public final Services services;
    public final Daos daos;
    public final Utils utils;
    public final Mock mock;
    public final Converter con;
    public final MockMvc mockMvc;
    public final ObjectMapper mapper;

    public AbstractTest(Services services,
                        Daos daos,
                        Controllers controllers,
                        Utils utils,
                        Mock mock,
                        Converter con,
                        MockMvc mockMvc,
                        ObjectMapper mapper) {
        this.services = services;
        this.daos = daos;
        this.controllers = controllers;
        this.utils = utils;
        this.con = con;
        this.mock = mock;
        this.mockMvc = mockMvc;
        this.mapper = mapper;
    }


    protected Person getRegisteredClient() {
        return getRegisteredPerson(mock.getPersonClient());
    }

    protected Person getRegisteredAdmin() {
        return getRegisteredPerson(mock.getPersonAdmin());
    }

    protected Person getRegisteredCook() {
        return getRegisteredPerson(mock.getPersonCook());
    }

    protected Person getRegisteredDeliveryman() {
        return getRegisteredPerson(mock.getPersonDeliveryman());
    }

    private Person getRegisteredPerson(Person person){
        var newPerson = services
                .personService
                .createPerson(person);

        var detachPerson = new Person(newPerson);
        detachPerson.setPassword(person.getPassword());
        return detachPerson;
    }

    protected MockHttpServletRequestBuilder addAuth(Person person,
                                                    MockHttpServletRequestBuilder requestBuilder) {
        var token = getTokenWithLogin(person);
        requestBuilder.header("Authorization", token);
        return requestBuilder;
    }

    protected MockHttpServletRequestBuilder sendObjectDto(Object object,
                                                          MockHttpServletRequestBuilder requestBuilder) throws JsonProcessingException {
        requestBuilder
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding(StandardCharsets.UTF_8)
                .content(mapper.writeValueAsString(object));
        return requestBuilder;
    }

    protected String getTokenWithLogin(Person person) {
        return controllers
                .authController
                .login(utils.getAuthRequest(person))
                .getAccessToken();
    }

    protected Addon getSavedAddon() {
        return services
                .dishService
                .createAddon(mock.getAddon());

    }


    protected Dish getSavedDish() {
        var dish = services
                .dishService
                .createDish(mock.getDish());
        var detachDish = new Dish(dish);
        return detachDish;
    }

    protected Action getSavedAction() {
        var dish = getSavedDish();
        var newPrice = 200;
        var action = mock.getAction();
        action.setDishes(List.of(dish));
        dish.getBasePortion().getPriceNow().setPrice(newPrice);
        action.setPortions(List.of(dish.getBasePortion()));

        var newAction = services
                .actionService
                .createAction(action);
        var detachAction = new Action(newAction);
        return detachAction;
    }

    protected Order getSavedOrder(Person person) {
        var dish = getSavedDish();
        var order = mock.getOrder();
        order.setClient(person);
        order.setDishes(List.of(new DishCount(null, dish, 1)));

        var newOrder = services
                .orderService
                .createOrder(order);
        var detachOrder = new Order(newOrder);
        return detachOrder;
    }

}
