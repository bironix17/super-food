package ru.bironix.super_food.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.bironix.super_food.db.dao.OrderDao;
import ru.bironix.super_food.db.models.dish.Addon;
import ru.bironix.super_food.db.models.dish.Dish;
import ru.bironix.super_food.db.models.dish.Portion;
import ru.bironix.super_food.db.models.dish.Price;
import ru.bironix.super_food.db.models.order.Order;
import ru.bironix.super_food.exceptions.DeletedDishInOrderException;
import ru.bironix.super_food.exceptions.InvalidDishInOrderException;
import ru.bironix.super_food.exceptions.InvalidTotalPriceException;
import ru.bironix.super_food.exceptions.NotFoundSourceException;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

@Service
public class
OrderService {

    @Autowired
    public OrderService(OrderDao orderDao) {
        this.orderDao = orderDao;
    }

    @Autowired
    EntityManager entityManager;

    @Autowired
    DishService dishService;

    @Autowired
    PersonService personService;

    final private OrderDao orderDao;

    public Order getOrder(int id) {
        return orderDao.findById(id).orElseThrow(() -> new NotFoundSourceException(id, "Order"));
    }

    public List<Order> getMyOrders(int id) {
        return orderDao.findAllByClientId(id);
    }

    //TODO изучить
    @Transactional
    public Order createOrder(Order order) {
        checkCorrectOrder(order);
        var createdOrder = orderDao.saveAndFlush(order);
        entityManager.refresh(createdOrder);
        return orderDao.findById(createdOrder.getId()).get();
    }

    private void checkCorrectOrder(Order order) {
        var dishesIds = order.getDishes().stream()
                .map(Dish::getId)
                .collect(toList());

        var dishes = dishService.getDishes(dishesIds);
        checkActualIds(order, dishes);
        checkDeleted(dishes);
        checkTotalPrice(order, dishes);
    }

    private void checkActualIds(Order order, List<Dish> dishes) {

        var invalidDishes = order.getDishes().stream()
                .filter(d -> dishes.stream()
                        .noneMatch(dDb -> dDb.forOrderEquals(d)))
                .collect(toList());

        if (!invalidDishes.isEmpty()) throw new InvalidDishInOrderException(invalidDishes);

        personService.getMe(order.getClient().getId());
        personService.getAddress(order.getAddress().getId());
    }


    private void checkDeleted(List<Dish> dishes) {
        var deletedIds = dishes.stream()
                .filter(Dish::getDeleted)
                .map(Dish::getId)
                .collect(toList());

        if (!deletedIds.isEmpty())
            throw new DeletedDishInOrderException(deletedIds);
    }

    private void checkTotalPrice(Order order, List<Dish> dishes) {
        var sum = dishes.stream()
                .map(Dish::getBasePortion)
                .map(Portion::getPriceNow)
                .mapToInt(Price::getPrice)
                .sum();

        var addonsDb = dishes.stream()
                .flatMap(d -> d.getAddons().stream())
                .collect(Collectors.toMap(Addon::getId, a -> a));

        sum += order.getDishes().stream()
                .flatMap(d -> d.getAddons().stream())
                .map(a -> addonsDb.get(a.getId()))
                .map(Addon::getPrice)
                .mapToInt(Price::getPrice)
                .sum();

        if (sum != order.getTotalPrice())
            throw new InvalidTotalPriceException();
    }


}
