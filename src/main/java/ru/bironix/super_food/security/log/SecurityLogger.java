package ru.bironix.super_food.security.log;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.bironix.super_food.dtos.AuthRequestDto;
import ru.bironix.super_food.store.db.models.order.Order;
import ru.bironix.super_food.store.db.models.person.Person;
import ru.bironix.super_food.utils.Utils;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

import static java.util.stream.Collectors.*;

@Component
public class SecurityLogger {
    final static Logger logger = LoggerFactory.getLogger(SecurityLogger.class);

    @Autowired
    private HttpServletRequest request;

    public void attemptRegistryPerson(AuthRequestDto authRequestDto) {
        var ip = Utils.getClientIP(request);
        logger.warn(String.format("Attempt registry from ip %s user with id %s", ip, authRequestDto.getEmail()));
    }

    public void registryPerson(Person person) {
        var ip = Utils.getClientIP(request);
        logger.warn(String.format("Successfully. Registered from ip %s user with id %s", ip, person.getId()));
    }


    public void attemptLoginPerson(AuthRequestDto authRequestDto) {
        var ip = Utils.getClientIP(request);
        logger.warn(String.format("Attempt login from ip %s user with id %s", ip, authRequestDto.getEmail()));
    }

    public void loginPerson(Person person) {
        var ip = Utils.getClientIP(request);
        logger.warn(String.format("Successfully. Login from ip %s user with id %s", ip, person.getId()));
    }


    private final String messageTemplate = "Successfully. From ip %s person with id %s %s with id %s";

    public void getPersonInformation(Integer requestingPersonId, Integer requestedPersonId) {
        var ip = Utils.getClientIP(request);
        logger.warn(String.format(messageTemplate,
                ip,
                requestingPersonId,
                "requested person data",
                requestedPersonId));
    }

    public void changePersonInformation(Integer requestingPersonId, Integer requestedPersonId) {
        var ip = Utils.getClientIP(request);
        logger.warn(String.format(messageTemplate,
                ip,
                requestingPersonId,
                "change person data",
                requestedPersonId));
    }

    public void createPerson(Integer requestingPersonId, Integer cratedPersonId) {
        var ip = Utils.getClientIP(request);
        logger.warn(String.format(messageTemplate,
                ip,
                requestingPersonId,
                "create person",
                cratedPersonId));
    }

    public void deletePerson(Integer requestingPersonId, Integer deletedPersonId) {
        var ip = Utils.getClientIP(request);
        logger.warn(String.format(messageTemplate,
                ip,
                requestingPersonId,
                "delete person",
                deletedPersonId));
    }

    public void addAddressForPerson(Integer personId, Integer addressId) {
        var ip = Utils.getClientIP(request);
        logger.warn(String.format(messageTemplate,
                ip,
                personId,
                "add new address",
                addressId));
    }

    public void deleteAddressForPerson(Integer personId, Integer addressId) {
        var ip = Utils.getClientIP(request);
        logger.warn(String.format(messageTemplate,
                ip,
                personId,
                "delete address",
                addressId));
    }

    public void getFavoritesForPerson(Integer requestingPersonId, Integer requestedPersonId) {
        var ip = Utils.getClientIP(request);
        logger.warn(String.format(messageTemplate,
                ip,
                requestingPersonId,
                "get favorites for person",
                requestedPersonId));
    }

    public void addFavoriteForPerson(Integer requestingPersonId, Integer requestedPersonId) {
        var ip = Utils.getClientIP(request);
        logger.warn(String.format(messageTemplate,
                ip,
                requestingPersonId,
                "add favorite for person",
                requestedPersonId));
    }

    public void deleteFavoriteForPerson(Integer requestingPersonId, Integer requestedPersonId) {
        var ip = Utils.getClientIP(request);
        logger.warn(String.format(messageTemplate,
                ip,
                requestingPersonId,
                "delete favorite for person",
                requestedPersonId));
    }

    public void createOrder(Integer personId, Integer orderId) {
        var ip = Utils.getClientIP(request);
        logger.warn(String.format(messageTemplate,
                ip,
                personId,
                "create order",
                orderId));
    }

    public void getOrder(Integer personId, Integer orderId) {
        var ip = Utils.getClientIP(request);
        logger.warn(String.format(messageTemplate,
                ip,
                personId,
                "get order",
                orderId));
    }

    public void changeOrder(Integer personId, Integer orderId) {
        var ip = Utils.getClientIP(request);
        logger.warn(String.format(messageTemplate,
                ip,
                personId,
                "change order",
                orderId));
    }

    public void deleteOrder(Integer personId, Integer orderId) {
        var ip = Utils.getClientIP(request);
        logger.warn(String.format(messageTemplate,
                ip,
                personId,
                "delete order",
                orderId));
    }

    public void getOrders(Integer personId, List<Order> orders) {
        var ip = Utils.getClientIP(request);
        logger.warn(String.format("Successfully. From ip %s person with id %s get orders with ids %s",
                ip,
                personId,
                StringUtils.join(orders.stream()
                        .map(Order::getId)
                        .collect(toList()), ", ")));
    }
}
