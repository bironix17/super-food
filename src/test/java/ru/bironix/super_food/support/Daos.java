package ru.bironix.super_food.support;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.bironix.super_food.store.db.dao.action.ActionDao;
import ru.bironix.super_food.store.db.dao.dish.AddonDao;
import ru.bironix.super_food.store.db.dao.dish.DishDao;
import ru.bironix.super_food.store.db.dao.dish.PortionDao;
import ru.bironix.super_food.store.db.dao.dish.PriceDao;
import ru.bironix.super_food.store.db.dao.order.OrderDao;
import ru.bironix.super_food.store.db.dao.person.AddressDao;
import ru.bironix.super_food.store.db.dao.person.FavoriteDao;
import ru.bironix.super_food.store.db.dao.person.PersonDao;

@Component
public class Daos {
    public final ActionDao actionDao;
    public final AddonDao addonDao;
    public final DishDao dishDao;
    public final PortionDao portionDao;
    public final PriceDao priceDao;
    public final OrderDao orderDao;
    public final AddressDao addressDao;
    public final FavoriteDao favoriteDao;
    public final PersonDao personDao;

    @Autowired
    public Daos(ActionDao actionDao,
                AddonDao addonDao,
                DishDao dishDao,
                PortionDao portionDao,
                PriceDao priceDao,
                OrderDao orderDao,
                AddressDao addressDao,
                FavoriteDao favoriteDao,
                PersonDao personDao) {
        this.actionDao = actionDao;
        this.addonDao = addonDao;
        this.dishDao = dishDao;
        this.portionDao = portionDao;
        this.priceDao = priceDao;
        this.orderDao = orderDao;
        this.addressDao = addressDao;
        this.favoriteDao = favoriteDao;
        this.personDao = personDao;
    }
}
