package ru.bironix.super_food.services;

import org.apache.commons.collections4.IteratorUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.bironix.super_food.db.dao.dish.AddonDao;
import ru.bironix.super_food.db.dao.dish.DishDao;
import ru.bironix.super_food.db.dao.dish.PortionDao;
import ru.bironix.super_food.db.dao.dish.PriceDao;
import ru.bironix.super_food.db.models.dish.Addon;
import ru.bironix.super_food.db.models.dish.Dish;
import ru.bironix.super_food.db.models.dish.Portion;
import ru.bironix.super_food.db.models.dish.Price;
import ru.bironix.super_food.exceptions.NotFoundSourceException;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static java.util.stream.Collectors.toList;

@Service
public class DishService {

    private final DishDao dishDao;
    private final AddonDao addonDao;
    private final PortionDao portionDao;
    private final PriceDao priceDao;

    @Autowired
    public DishService(DishDao dishDao, AddonDao addonDao, PortionDao portionDao, PriceDao priceDao) {
        this.dishDao = dishDao;
        this.addonDao = addonDao;
        this.portionDao = portionDao;
        this.priceDao = priceDao;
    }

    public Dish getDish(int id) {
        return dishDao.findById(id).orElseThrow(() -> new NotFoundSourceException(id, "Dish"));
    }

    public List<Dish> getDishes() {
        return IteratorUtils.toList(dishDao.findAll().iterator());
    }


    public Addon createAddon(Addon addon) {
        return addonDao.save(addon);
    }

    public List<Addon> getAddons() {
        List<Addon> result = new ArrayList<>();
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

        if (!portionDao.existsById(portion.getId()))
            throw new NotFoundSourceException(portion.getId(), "Dish");

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
        dishDao.save(resetDataForDish(dishOpt.get()));
        return true;
    }

    private Dish resetDataForDish(Dish dish) {
        return Dish.builder()
                .id(dish.getId())
                .picturePaths(dish.getPicturePaths())
                .name(dish.getName())
                .deleted(true)
                .build();
    }

    public List<Dish> getDishes(Set<Integer> ids) {
        var dishes = IteratorUtils.toList(dishDao.findAllById(ids).iterator());
        if (dishes.size() != ids.size()) {
            var idsFromBd = dishes.stream().map(Dish::getId).collect(toList());
            var notExistIds = ids.stream().filter(id -> !idsFromBd.contains(id)).collect(toList());
            throw new NotFoundSourceException(notExistIds, "Dish");
        }
        return dishes;
    }
}
