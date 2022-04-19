package ru.bironix.super_food.services;

import org.apache.commons.collections4.IteratorUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.bironix.super_food.db.dish.dao.AddonDao;
import ru.bironix.super_food.db.dish.dao.DishDao;
import ru.bironix.super_food.db.dish.dao.PortionDao;
import ru.bironix.super_food.db.dish.dao.PriceDao;
import ru.bironix.super_food.db.dish.models.Addon;
import ru.bironix.super_food.db.dish.models.Dish;
import ru.bironix.super_food.db.dish.models.Portion;
import ru.bironix.super_food.db.dish.models.Price;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
public class DishService {

    @Autowired
    DishDao dishDao;

    @Autowired
    AddonDao addonDao;

    @Autowired
    PortionDao portionDao;

    @Autowired
    PriceDao priceDao;

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

        var basePortionIndex = dish.getPortions().indexOf(dish.getBasePortion());
        portionDao.saveAll(dish.getPortions());
        dish.setBasePortion(dish.getPortions().get(basePortionIndex));
        return dishDao.save(dish);
    }


    public boolean updatePriceForDishPortion(Portion portion, int price) {
        portion.setOldPrice(portion.getPriceNow());
        var newPrice = new Price(null, price);
        priceDao.save(newPrice); //TODO изучить почему без этого id сам не вставляется

        portion.setPriceNow(newPrice);
        portionDao.save(portion);
        return true;
    }




    public boolean deleteDish(int id) {
        var dishOpt = dishDao.findById(id);
        if (dishOpt.isEmpty()) return false;
        dishDao.save(resetFataForDish(dishOpt.get()));
        return true;
    }

    private Dish resetFataForDish(Dish dish) {
        return Dish.builder()
                .id(dish.getId())
                .picturePaths(dish.getPicturePaths())
                .name(dish.getName())
                .deleted(true)
                .build();
    }
}
