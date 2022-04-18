package ru.bironix.super_food;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import ru.bironix.super_food.db.dish.dao.AddonDao;
import ru.bironix.super_food.db.dish.dao.DishDao;
import ru.bironix.super_food.db.dish.models.*;
import ru.bironix.super_food.db.generalModels.PicturePaths;
import ru.bironix.super_food.services.DishService;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
public class CreateDish {

    @Autowired
    DishService dishService;

    @Autowired
    DishDao dishDao;

    @Autowired
    AddonDao addonDao;


    //TODO сделать нормальные методы для тестового насыщения бд
    @Test
    @Transactional
    @Rollback(value = false)
    public void createDish() {

        var portion = new Portion(null, "medium", new Price(null, 100), null);
        var addon = new Addon(null, "kva", " ", new Price(null, 1));
        var dish = new Dish(null, new PicturePaths(null, null, null, "dsds"),
                "Burger", "dsdsd", CategoryType.BURGERS, "ds", "ds",
                0, List.of(portion), List.of(addon), null, false, new ArrayList<>());

        dishService.createAddon(addon);
        dishService.createDish(dish);

        var dish1 = new Dish(null, new PicturePaths(null, null, null, "dsds"),
                "Burger", "dsdsd", CategoryType.BURGERS, "ds", "ds",
                0, List.of(portion), List.of(addon) // Вот повторяющиеся addon
                , List.of(dish), false, new ArrayList<>());

        dishService.createDish(dish1); //здесь падает


        Assert.assertEquals(dish.getId() != null, true);
    }
}
