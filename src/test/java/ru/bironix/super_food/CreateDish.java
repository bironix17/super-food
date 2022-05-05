package ru.bironix.super_food;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import ru.bironix.super_food.db.dao.dish.AddonDao;
import ru.bironix.super_food.db.dao.dish.DishDao;
import ru.bironix.super_food.db.models.PicturePaths;
import ru.bironix.super_food.db.models.dish.*;
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

//
//    //TODO сделать нормальные методы для тестового насыщения бд
//    @Test
//    @Transactional
//    @Rollback(value = false)
//    public void createDish() {
//
//        var portion = new Portion(null, "medium", new Price(null, 100), null);
//        var addon = new Addon(null, "kva", " ", new Price(null, 1));
//        var dish = new Dish(null, new PicturePaths(null, null, null, "dsds"),
//                "Burger", "dsdsd", CategoryType.BURGERS, "ds", "ds",
//                portion, List.of(portion), new ArrayList<>(List.of(addon)), null, false, new ArrayList<>());
//
//        dishService.createAddon(addon);
//        dishService.createDish(dish);
//
//        var portion1 = new Portion(null, "medium", new Price(null, 100), null);
//        var dish1 = new Dish(null, new PicturePaths(null, null, null, "dsds"),
//                "Burger", "dsdsd", CategoryType.BURGERS, "ds", "ds",
//                portion1, List.of(portion1), null // Вот повторяющиеся addon
//                , List.of(dish), false, new ArrayList<>());
//
//        dishService.createDish(dish1); //здесь падает
////
////        var z = dishDao.findAllByAddonsContains(addon);
////
////        z.forEach(dish2 -> {
////            dish2.getAddons().remove(addon);
////        });
////        addonDao.delete(addon);
//
//        Assert.assertEquals(dish.getId() != null, true);
//    }
}
