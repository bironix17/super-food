package ru.bironix.super_food.services;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.IteratorUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.bironix.super_food.constants.ApiError;
import ru.bironix.super_food.store.db.dao.dish.AddonDao;
import ru.bironix.super_food.store.db.dao.dish.DishDao;
import ru.bironix.super_food.store.db.dao.dish.PortionDao;
import ru.bironix.super_food.store.db.dao.dish.PriceDao;
import ru.bironix.super_food.store.db.models.dish.*;
import ru.bironix.super_food.store.UpdateMapper;
import ru.bironix.super_food.exceptions.ApiException;
import ru.bironix.super_food.exceptions.NotFoundSourceException;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.toList;

@Service
public class DishService {

    private final DishDao dishDao;
    private final AddonDao addonDao;
    private final PortionDao portionDao;
    private final PriceDao priceDao;
    private final EntityManager entityManager;
    private final UpdateMapper updateMapper;

    @Autowired
    public DishService(DishDao dishDao,
                       AddonDao addonDao,
                       PortionDao portionDao,
                       PriceDao priceDao,
                       UpdateMapper updateMapper,
                       EntityManager entityManager) {
        this.dishDao = dishDao;
        this.addonDao = addonDao;
        this.portionDao = portionDao;
        this.priceDao = priceDao;
        this.entityManager = entityManager;
        this.updateMapper = updateMapper;
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

    @Transactional
    public Dish createDish(Dish dish) {

        checkComboCorrect(dish);

        var basePortionIndex = IntStream.range(0, dish.getPortions().size())
                .filter(userInd -> dish.getPortions().get(userInd).getSize().equals(dish.getBasePortion().getSize()))
                .findFirst()
                .orElse(0);

        portionDao.saveAll(dish.getPortions());
        dish.setBasePortion(dish.getPortions().get(basePortionIndex));
        dishDao.saveAndFlush(dish);
        entityManager.refresh(dish);
        return dish;
    }

    private void checkComboCorrect(Dish dish) {
        if (dish.getCategory() == CategoryType.COMBO
                && CollectionUtils.isNotEmpty(dish.getDishes()))
            throw new ApiException(ApiError.ONLY_COMBO_CAN_CONTAIN_DISHES);
    }

    @Transactional
    public Portion createNewPriceForAction(Portion portion) {

        if (!portionDao.existsById(portion.getId()))
            throw new NotFoundSourceException(portion.getId(), "Portion");

        var newPrice = portion.getPriceNow().getPrice();
        var portionDb = getPortion(portion.getId());

        portionDb.setOldPrice(portion.getPriceNow());
        portionDb.setPriceNow(new Price(null, newPrice));

        return portionDb;
    }

    @Transactional
    public Portion deleteNewPriceForAction(Portion portion) {

        if (!portionDao.existsById(portion.getId()))
            throw new NotFoundSourceException(portion.getId(), "Portion");

        var portionDb = getPortion(portion.getId());

        portionDb.setPriceNow(portion.getOldPrice());
        portionDb.setOldPrice(null);

        return portionDb;
    }


    private Portion getPortion(Integer id) {
        return portionDao.findById(id).orElseThrow(() ->
                new NotFoundSourceException(id, "Portion"));
    }


    public void deleteDish(int id) {
        var dish = getDish(id);
        dishDao.saveAndFlush(resetDataForDeletedDish(dish));
    }

    private Dish resetDataForDeletedDish(Dish dish) {
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

    public Addon getAddon(int id) {
        return addonDao.findById(id).orElseThrow(() ->
                new NotFoundSourceException(id, "Addon"));
    }

    @Transactional
    public Addon updateAddon(Addon addon) {
        var addonDb = getAddon(addon.getId());
        updateMapper.map(addon, addonDb);
        return addonDb;
    }

    @Transactional
    public void deleteAddon(int id) {
        var addon = getAddon(id);
        addon.setDeleted(true);

    }

    @Transactional
    public Dish updateDish(Dish dish) {
        var dishDb = getDish(dish.getId());
        updateMapper.map(dish, dishDb);
        return dishDb;
    }

    public List<Dish> getActualDishes() {
        return dishDao.findByDeleted(false);
    }
}
