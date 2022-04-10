package ru.bironix.super_food.services;

import org.apache.commons.collections4.IteratorUtils;
import org.springframework.beans.PropertyValues;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.bironix.super_food.Utils;
import ru.bironix.super_food.converters.DishConverter;
import ru.bironix.super_food.db.dish.dao.AddonDao;
import ru.bironix.super_food.db.dish.dao.DishDao;
import ru.bironix.super_food.db.dish.models.Addon;
import ru.bironix.super_food.db.dish.models.Dish;
import ru.bironix.super_food.dtos.dish.*;
import ru.bironix.super_food.dtos.responses.DishesInCategoriesDto;

import java.util.ArrayList;
import java.util.List;

@Service
public class DishService {

    @Autowired
    DishDao dishDao;

    @Autowired
    AddonDao addonDao;

    public Dish getFullDish(int id) {
        return dishDao.findById(Integer.valueOf(id)).orElse(null);
    }

    public List<Dish> getAllDishes() {
        return IteratorUtils.toList(dishDao.findAll().iterator());
    }


    public Addon createAddon(Addon addon) {
        return addonDao.save(addon);
    }

    public List<Addon> getAllAddons() {
        List<Addon> result = new ArrayList<Addon>();
        addonDao.findAll().forEach(result::add);
        return result;
    }


    public Dish createDish(Dish dish) {
        return dishDao.save(dish);
    }
}
