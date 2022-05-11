package ru.bironix.super_food.oldVersion.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.bironix.super_food.converters.Converter;
import ru.bironix.super_food.store.db.dao.dish.AddonDao;
import ru.bironix.super_food.store.db.dao.dish.DishDao;
import ru.bironix.super_food.store.db.models.action.Action;
import ru.bironix.super_food.store.db.models.common.PicturePaths;
import ru.bironix.super_food.store.db.models.dish.*;
import ru.bironix.super_food.store.db.models.person.Address;
import ru.bironix.super_food.store.db.models.person.Person;


import java.util.List;

@Component
public class CreateUtils {

    @Autowired
    AddonDao addonDao;

    @Autowired
    DishDao dishDao;

    @Autowired
    public Converter con;

    public ObjectMapper mapper = new ObjectMapper();

    private static final String PICTURE_PATH = "https://static.wikia.nocookie.net/edopedia/images/7/7b/%D0%9F%D0%B8%D1%86%D1%86%D0%B0.JPG/revision/latest?cb=20170530063638&path-prefix=ru";

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

    public Portion getPortion() {
        return Portion.builder()
                .size("100 грамм")
                .priceNow(getPrice())
                .oldPrice(getPrice())
                .build();
    }

    public Addon getAddon() {
        return Addon.builder()
                .name("Морковь")
                .picturePath(PICTURE_PATH)
                .price(getPrice())
                .build();
    }

    public Dish getDish() {
        var addon = addonDao.save(getAddon());
        return Dish.builder()
                .picturePaths(getPicturePaths())
                .name("Пицца")
                .composition("булочка, 3 сыра")
                .category(CategoryType.PIZZA)
                .description("Вкусная")
                .allergens("Молоко")
                .basePortion(getPortion())
                .portions(List.of(getPortion(), getPortion()))
                .addons(List.of(addon))
                .dishes(List.of())
                .deleted(false)
                .build();
    }

    public Action getAction() {
        var dish = dishDao.save(getDish());
        return Action.builder()
                .name("Скидкаааа")
                .picturePaths(getPicturePaths())
                .dishes(List.of(dish))
                .build();
    }

    public Address getAddress() {
        return Address.builder()
                .address("Москва, Питерская")
                .build();
    }

    public Person getPerson() {
        return Person.builder()
                .name("Степан")
                .password("Степа777")
                .email("stepa@rambler.ru")
                .addresses(List.of(getAddress()))
                .build();
    }
}