package ru.bironix.super_food.support;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.bironix.super_food.constants.Constants;
import ru.bironix.super_food.converters.Converter;
import ru.bironix.super_food.store.db.models.action.Action;
import ru.bironix.super_food.store.db.models.common.PicturePaths;
import ru.bironix.super_food.store.db.models.dish.*;
import ru.bironix.super_food.store.db.models.order.Order;
import ru.bironix.super_food.store.db.models.order.WayToGet;
import ru.bironix.super_food.store.db.models.person.Person;
import ru.bironix.super_food.store.db.models.person.Role;

import java.time.LocalDateTime;
import java.util.List;

@Component
public class Mock {
    public final Converter con;

    private static final String PICTURE_PATH = "https://static.wikia.nocookie.net/edopedia/images/7/7b/%D0%9F%D0%B8%D1%86%D1%86%D0%B0.JPG/revision/latest?cb=20170530063638&path-prefix=ru";

    @Autowired
    public Mock(Converter con) {
        this.con = con;
    }

    public Order getOrder() {
        return Order.builder()
                .created(LocalDateTime.now())
                .deliveryTime(LocalDateTime.now())
                .totalPrice(100)
                .wayToGet(WayToGet.PICKUP)
                .build();
    }

    public Dish getDish() {
        return Dish.builder()
                .picturePaths(getPicturePaths())
                .name("Пицца")
                .composition("булочка, 3 сыра")
                .category(new Category(Constants.PIZZA))
                .description("Вкусная")
                .allergens("Молоко")
                .basePortion(getPortion())
                .portions(List.of(getPortion(), getPortion()))
                .dishes(List.of())
                .deleted(false)
                .build();
    }

    public Action getAction() {
        return Action.builder()
                .name("Скидка на всё, кроме чего то там...")
                .picturePaths(getPicturePaths())
                .build();
    }

    public PicturePaths getPicturePaths() {
        return PicturePaths.builder()
                .large(PICTURE_PATH)
                .build();
    }

    public Price getPrice() {
        return Price.builder()
                .price(100)
                .build();
    }

    public Price getPrice(int value) {
        return Price.builder()
                .price(value)
                .build();
    }

    public Portion getPortion() {
        return Portion.builder()
                .size("100 грамм")
                .priceNow(getPrice())
                .deleted(false)
                .build();
    }

    public Addon getAddon() {
        return Addon.builder()
                .name("Морковь")
                .picturePath(PICTURE_PATH)
                .priceNow(getPrice())
                .prices(List.of(getPrice()))
                .build();
    }

    public Person getPersonClient() {
        return Person.builder()
                .password("client")
                .phoneNumber("+79180101111")
                .name("Игорь")
                .role(Role.ROLE_CLIENT)
                .build();
    }


    public Person getPersonAdmin() {
        return Person.builder()
                .phoneNumber("+79001234567")
                .password("admin")
                .role(Role.ROLE_ADMIN)
                .build();
    }

    public Person getPersonCook() {
        return Person.builder()
                .phoneNumber("+79001234561")
                .password("cook")
                .role(Role.ROLE_COOK)
                .build();
    }

    public Person getPersonDeliveryman() {
        return Person.builder()
                .phoneNumber("+79001234562")
                .password("deliveryman")
                .role(Role.ROLE_DELIVERYMAN)
                .build();
    }
    public Person getPersonManager() {
        return Person.builder()
                .phoneNumber("+79031234562")
                .password("manager")
                .role(Role.ROLE_MANAGER)
                .build();
    }
}