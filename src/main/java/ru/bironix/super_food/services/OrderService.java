package ru.bironix.super_food.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ru.bironix.super_food.constants.ApiError;
import ru.bironix.super_food.constants.Constants;
import ru.bironix.super_food.store.db.dao.order.OrderDao;
import ru.bironix.super_food.store.db.models.dish.Addon;
import ru.bironix.super_food.store.db.models.dish.Dish;
import ru.bironix.super_food.store.db.models.dish.DishCount;
import ru.bironix.super_food.store.db.models.dish.Portion;
import ru.bironix.super_food.store.db.models.order.Order;
import ru.bironix.super_food.store.db.models.order.OrderStatus;
import ru.bironix.super_food.store.db.models.order.WayToGet;
import ru.bironix.super_food.store.db.models.person.Person;
import ru.bironix.super_food.store.UpdateMapper;
import ru.bironix.super_food.exceptions.ApiException;
import ru.bironix.super_food.exceptions.DeletedDishInOrderException;
import ru.bironix.super_food.exceptions.InvalidEntitiesOrderException;
import ru.bironix.super_food.exceptions.NotFoundSourceException;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Stream;

import static java.util.stream.Collectors.*;
import static ru.bironix.super_food.constants.Constants.PAGE_SIZE;

@Service
public class
OrderService {

    private final EntityManager entityManager;
    private final DishService dishService;
    private final PersonService personService;
    private final OrderDao orderDao;
    private final UpdateMapper updateMapper;
    private final InformationService informationService;

    @Autowired
    public OrderService(EntityManager entityManager,
                        DishService dishService,
                        PersonService personService,
                        InformationService informationService,
                        UpdateMapper updateMapper,
                        OrderDao orderDao) {
        this.entityManager = entityManager;
        this.dishService = dishService;
        this.personService = personService;
        this.informationService = informationService;
        this.orderDao = orderDao;
        this.updateMapper = updateMapper;
    }

    public Order getOrder(int id) {
        return orderDao.findById(id).orElseThrow(() -> new NotFoundSourceException(id, "Order"));
    }

    public List<Order> getOrdersForPerson(Person person, int pageNumber) {
        var page = PageRequest.of(pageNumber, PAGE_SIZE);
        return orderDao.findByClient_IdOrderByCreatedDesc(person.getId(), page);
    }

    @Transactional
    public Order createOrder(Order order) {
        var newOrder = new Order(order);
        checkCorrectOrder(newOrder);

        if (newOrder.getWayToGet() == WayToGet.DELIVERY
                && newOrder.getAddress() != null) {

            if (newOrder.getAddress().getAddress() != null) {
                var address =
                        personService.addAddressForPerson(newOrder.getClient().getId(),
                                newOrder.getAddress().getAddress());
                newOrder.setAddress(address);
            }
        } else if (newOrder.getWayToGet() == WayToGet.PICKUP) {
            newOrder.setAddress(null);
        }

        newOrder.setCreated(LocalDateTime.now());

        var createdOrder = orderDao.saveAndFlush(newOrder);
        entityManager.refresh(createdOrder);
        return orderDao.findById(createdOrder.getId()).get();
    }

    private void checkCorrectOrder(Order order) {
        var dishesIds = order.getDishes().stream()
                .map(DishCount::getDish)
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
                .map(DishCount::getDish)
                .filter(d -> dishesDb.stream()
                        .noneMatch(dDb -> dDb.forOrderEquals(d)))
                .collect(toList());

        if (!invalidDishes.isEmpty()) {
            throw new InvalidEntitiesOrderException(invalidDishes.stream()
                    .map(Dish::getId)
                    .collect(toList()), ApiError.INCORRECT_DATA_FOR_DISH);
        }
        personService.getPerson(order.getClient().getId());
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
                .flatMap(dc -> Stream.generate(dc::getDish).limit(dc.getCount()))
                .peek(d -> d.setBasePortion(
                        portionsDb.get(d.getBasePortion().getId())
                ))
                .peek(d -> d.setAddons(
                        d.getAddons().stream()
                                .map(a -> addonsDb.get(a.getId()))
                                .collect(toList()))
                ).mapToInt(Dish::getTotalPrice)
                .sum();

        if (order.getWayToGet() == WayToGet.DELIVERY) {
            sum += informationService.getDeliveryInformation().getDeliveryPrice();
        }


        if (sum != order.getTotalPrice())
            throw new ApiException(ApiError.INVALID_TOTAL_PRICE);
    }

    private void checkAddress(Order order) {
        if (order.getWayToGet() == WayToGet.DELIVERY &&
                order.getAddress() == null &&
                order.getAddress().getId() == null &&
                order.getAddress().getAddress() == null) {
            throw new ApiException(ApiError.ADDRESS_REQUIRED);
        }
    }


    @Transactional
    public Order updateOrder(Order order) {
        var orderBd = getOrder(order.getId());
        updateMapper.map(order, orderBd);
        return orderBd;
    }


    public void deleteOrder(int id) {
        var order = getOrder(id);
        orderDao.delete(order);
    }

    public List<Order> getActiveOrders(int pageNumber) {
        var page = PageRequest.of(pageNumber, PAGE_SIZE);
        return orderDao.findByStatusNot(OrderStatus.COMPLETED, page);
    }

    public List<Order> getOrdersByStatus(OrderStatus status, int pageNumber) {
        var page = PageRequest.of(pageNumber, PAGE_SIZE);
        return orderDao.findByStatus(status, page);
    }
}
