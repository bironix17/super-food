package ru.bironix.super_food.services;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import ru.bironix.super_food.constants.ApiError;
import ru.bironix.super_food.exceptions.*;
import ru.bironix.super_food.store.UpdateMapper;
import ru.bironix.super_food.store.db.dao.dish.DishCountDao;
import ru.bironix.super_food.store.db.dao.order.OrderDao;
import ru.bironix.super_food.store.db.models.dish.Addon;
import ru.bironix.super_food.store.db.models.dish.Dish;
import ru.bironix.super_food.store.db.models.dish.DishCount;
import ru.bironix.super_food.store.db.models.dish.Portion;
import ru.bironix.super_food.store.db.models.order.Order;
import ru.bironix.super_food.store.db.models.order.OrderStatus;
import ru.bironix.super_food.store.db.models.order.WayToGet;
import ru.bironix.super_food.store.db.models.person.Address;
import ru.bironix.super_food.store.db.models.person.Person;
import ru.bironix.super_food.store.utilsModel.EntitiesWithCount;

import javax.persistence.EntityManager;
//import javax.transaction.Transactional;
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
    private final DishCountDao dishCountDao;
    private final UpdateMapper updateMapper;
    private final InformationService informationService;

    @Autowired
    public OrderService(EntityManager entityManager,
                        DishService dishService,
                        PersonService personService,
                        InformationService informationService,
                        DishCountDao dishCountDao,
                        UpdateMapper updateMapper,
                        OrderDao orderDao) {
        this.entityManager = entityManager;
        this.dishService = dishService;
        this.personService = personService;
        this.informationService = informationService;
        this.orderDao = orderDao;
        this.updateMapper = updateMapper;
        this.dishCountDao = dishCountDao;
    }

    public Order getOrder(int id) {
        return orderDao.findById(id).orElseThrow(() -> new NotFoundSourceException(id, "Order"));
    }

    public EntitiesWithCount getOrdersForPerson(Person person, int pageNumber) {
        var page = PageRequest.of(pageNumber, PAGE_SIZE);
        var pageOrders = orderDao.findByClient_IdOrderByCreatedDesc(person.getId(), page);
        return new EntitiesWithCount<Order>(pageOrders.getContent(), pageOrders.getTotalElements());
    }


    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public Order createOrder(Order order) {
        var newOrder = new Order(order);
        checkCorrectOrder(newOrder);

        if (newOrder.getWayToGet() == WayToGet.DELIVERY
                && newOrder.getAddress() != null) {

            if (newOrder.getAddress().getAddress() != null) {
                Address address;
                try {
                    address = personService.addAddressForPerson( //TODO починить транзакции
                            newOrder.getClient().getId(),
                            newOrder.getAddress().getAddress());
                } catch (ApiException e) {
                    address = personService.getAddressByAddressValue(newOrder.getAddress().getAddress());
                }

                newOrder.setAddress(address);
            }
        } else if (newOrder.getWayToGet() == WayToGet.PICKUP) {
            newOrder.setAddress(null);
        }

        newOrder.setCreated(LocalDateTime.now());

        var createdOrder = orderDao.saveAndFlush(newOrder);
        entityManager.refresh(createdOrder);
        return createdOrder;
    }

    private void checkCorrectOrder(Order order) {
        var dishesIds = order.getDishes().stream()
                .map(DishCount::getDish)
                .map(Dish::getId)
                .collect(toSet());

        var dishesDb = dishService.getDishes(dishesIds);
        checkActualIds(order, dishesDb);
        checkDeleted(order, dishesDb);
        checkTotalPrice(order, dishesDb);
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


    private void checkDeleted(Order order, List<Dish> dishesDb) {
        var deletedDishesInOrderIds = dishesDb.stream()
                .filter(Dish::getDeleted)
                .map(Dish::getId)
                .collect(toList());

        if (!deletedDishesInOrderIds.isEmpty())
            throw new DeletedDishesInOrderException(deletedDishesInOrderIds);

        var orderAddonsIds = order.getDishes().stream()
                .map(DishCount::getDish)
                .flatMap(d -> d.getAddons().stream())
                .map(Addon::getId)
                .collect(toList());

        var deletedAddonsOnOrderIds = dishesDb.stream()
                .flatMap(d -> d.getAddons().stream())
                .filter(Addon::getDeleted)
                .map(Addon::getId)
                .filter(orderAddonsIds::contains)
                .collect(toList());


        if (!deletedAddonsOnOrderIds.isEmpty())
            throw new DeletedAddonsInOrderException(deletedAddonsOnOrderIds);

    }

    private void checkTotalPrice(Order order, List<Dish> dishesDb) {

        var portionsDb = dishesDb.stream()
                .flatMap(d -> d.getPortions().stream())
                .collect(toMap(Portion::getId, p -> p));

        var addonsDb = dishesDb.stream()
                .flatMap(d -> d.getAddons().stream())
                .collect(toMap(Addon::getId, p -> p));

        var sum = order.getDishes().stream()
                .flatMap(dc -> Stream.generate(() -> {

                            var dish = dc.getDish();
                            dish.setBasePortion(
                                    portionsDb.get(dc.getPortion().getId()));

                            dish.setAddons(
                                    dish.getAddons().stream()
                                            .map(a -> addonsDb.get(a.getId()))
                                            .collect(toList()));
                            return dish;
                        }
                ).limit(dc.getCount()))
                .mapToInt(Dish::getTotalPrice)
                .sum();

        if (order.getWayToGet() == WayToGet.DELIVERY) {
            sum += informationService.getDeliveryInformation().getDeliveryPrice();
        }


        if (sum != order.getTotalPrice())
            throw new ApiException(ApiError.INVALID_TOTAL_PRICE);
    }

    private void checkAddress(Order order) {
        if (order.getWayToGet() == WayToGet.DELIVERY &&
                (order.getAddress() == null ||
                        order.getAddress().getId() == null &&
                                order.getAddress().getAddress() == null)) {
            throw new ApiException(ApiError.ADDRESS_REQUIRED);
        }
    }


    @Transactional
    public Order updateOrder(Order order) {
        var orderBd = getOrder(order.getId());

        if (CollectionUtils.isNotEmpty(order.getDishes())) {
            order.getDishes().forEach(dc -> {
                if (dc.getId() == null)
                    dishCountDao.save(dc);
            });
        }

        updateMapper.map(order, orderBd);

        checkCorrectOrder(orderBd);

        orderDao.saveAndFlush(orderBd);
        entityManager.refresh(orderBd);
        return orderBd;
    }


    public void deleteOrder(int id) {
        var order = getOrder(id);
        orderDao.delete(order);
    }

    public List<Order> getActiveOrders(int pageNumber) {
        var page = PageRequest.of(pageNumber, PAGE_SIZE);
        return orderDao.findByStatusNotIn(List.of(OrderStatus.COMPLETED, OrderStatus.CANCELED), page);
    }

    public List<Order> getOrdersByStatus(OrderStatus status, int pageNumber) {
        var page = PageRequest.of(pageNumber, PAGE_SIZE);
        return orderDao.findByStatus(status, page);
    }

}
