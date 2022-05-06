package ru.bironix.super_food.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.bironix.super_food.db.dao.order.OrderDao;
import ru.bironix.super_food.db.models.dish.Addon;
import ru.bironix.super_food.db.models.dish.Dish;
import ru.bironix.super_food.db.models.dish.Portion;
import ru.bironix.super_food.db.models.order.Order;
import ru.bironix.super_food.db.models.order.WayToGet;
import ru.bironix.super_food.db.models.person.Person;
import ru.bironix.super_food.exceptions.*;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.List;

import static java.util.stream.Collectors.*;

@Service
public class
OrderService {

    private final EntityManager entityManager;
    private final DishService dishService;
    private final PersonService personService;
    private final OrderDao orderDao;

    @Autowired
    public OrderService(EntityManager entityManager,
                        DishService dishService,
                        PersonService personService,
                        OrderDao orderDao) {
        this.entityManager = entityManager;
        this.dishService = dishService;
        this.personService = personService;
        this.orderDao = orderDao;
    }

    public Order getOrder(int id) {
        return orderDao.findById(id).orElseThrow(() -> new NotFoundSourceException(id, "Order"));
    }

    public List<Order> getOrdersForPerson(Person person) {
        return orderDao.findAllByClientId(person.getId());
    }

    @Transactional
    public Order createOrder(Order order) {
        checkCorrectOrder(order);

        if (order.getWayToGet() == WayToGet.DELIVERY
                && order.getAddress() != null) {

            if (order.getAddress().getAddress() != null) {
                var address =
                        personService.addAddressForPerson(order.getClient().getEmail(), order.getAddress().getAddress());
                order.setAddress(address);
            }
        } else if (order.getWayToGet() == WayToGet.PICKUP) {
            order.setAddress(null);
        }

        var createdOrder = orderDao.saveAndFlush(order);
        entityManager.refresh(createdOrder);
        return orderDao.findById(createdOrder.getId()).get();
    }

    private void checkCorrectOrder(Order order) {
        var dishesIds = order.getDishes().stream()
                .map(Dish::getId)
                .collect(toSet());

        var dishes = dishService.getDishes(dishesIds);
        checkActualIds(order, dishes);
        checkDeleted(dishes);
        checkTotalPrice(order, dishes);
        checkAddress(order);
    }


    private void checkActualIds(Order order, List<Dish> dishesDb) {

        var invalidDishes = order.getDishes().stream()
                .filter(d -> dishesDb.stream()
                        .noneMatch(dDb -> dDb.forOrderEquals(d)))
                .collect(toList());

        if (!invalidDishes.isEmpty()) {
            throw new InvalidDishInOrderException(invalidDishes.stream()
                    .map(Dish::getId)
                    .collect(toList()));
        }
        personService.getById(order.getClient().getId());
    }


    private void checkDeleted(List<Dish> dishesDb) {
        var deletedIds = dishesDb.stream()
                .filter(Dish::getDeleted)
                .map(Dish::getId)
                .collect(toList());

        if (!deletedIds.isEmpty())
            throw new DeletedDishInOrderException(deletedIds);
    }

    private void checkTotalPrice(Order order, List<Dish> dishesDb) {

        var portionsDb = dishesDb.stream()
                .flatMap(d -> d.getPortions().stream())
                .collect(toMap(Portion::getId, p -> p));

        var addonsDb = dishesDb.stream()
                .flatMap(d -> d.getAddons().stream())
                .collect(toMap(Addon::getId, p -> p));

        var sum = order.getDishes().stream()
                .peek(d -> d.setBasePortion(
                        portionsDb.get(d.getBasePortion().getId())
                ))
                .peek(d -> d.setAddons(
                        d.getAddons().stream()
                                .map(a -> addonsDb.get(a.getId()))
                                .collect(toList()))
                )
                .mapToInt(Dish::getTotalPrice)
                .sum();


        if (sum != order.getTotalPrice())
            throw new InvalidTotalPriceException();
    }

    private void checkAddress(Order order) {
        if (order.getWayToGet() == WayToGet.DELIVERY &&
                order.getAddress() == null &&
                order.getAddress().getId() == null &&
                order.getAddress().getAddress() == null) {
            throw new AddressRequiredException();
        }
    }


}
