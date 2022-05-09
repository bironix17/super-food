package ru.bironix.super_food.services;

import org.apache.commons.collections4.IteratorUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.bironix.super_food.db.dao.action.ActionDao;
import ru.bironix.super_food.db.models.action.Action;
import ru.bironix.super_food.exceptions.NotFoundSourceException;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.List;

@Service
public class ActionService {

    private final ActionDao actionDao;
    private final DishService dishService;
    private final EntityManager entityManager;

    @Autowired
    public ActionService(ActionDao actionDao,
                         DishService dishService,
                         EntityManager entityManager) {
        this.actionDao = actionDao;
        this.dishService = dishService;
        this.entityManager = entityManager;
    }

    public List<Action> getActions() {
        return IteratorUtils.toList(actionDao.findAll().iterator());
    }

    public Action getAction(int id) {
        return actionDao.findById(id).orElseThrow(() -> new NotFoundSourceException(id, "Action"));
    }

    @Transactional
    public Action createAction(Action action, int newPrice) {
        action.getDishes()
                .forEach(dish -> {
                    var basePortion = dish.getBasePortion();
                    dishService.updatePriceForDishPortion(basePortion, newPrice);
                });

        actionDao.saveAndFlush(action);
        entityManager.refresh(action);
        return action;
    }
}