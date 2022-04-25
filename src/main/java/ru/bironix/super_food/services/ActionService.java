package ru.bironix.super_food.services;

import org.apache.commons.collections4.IteratorUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.bironix.super_food.db.dao.ActionDao;
import ru.bironix.super_food.db.models.Action;
import ru.bironix.super_food.exceptions.NotFoundSource;

import java.util.ArrayList;
import java.util.List;

@Service
public class ActionService {

    @Autowired
    public ActionService(ActionDao actionDao, DishService dishService) {
        this.actionDao = actionDao;
        this.dishService = dishService;
    }

    final ActionDao actionDao;
    final DishService dishService;

    public List<Action> getActions() {
        return IteratorUtils.toList(actionDao.findAll().iterator());
    }

    public Action getAction(int id) {
        return actionDao.findById(id).orElseThrow(NotFoundSource::new);
    }

    public Action createAction(Action action, int newPrice) {
        action.getDishes()
                .forEach(dish -> {
                    var basePortion = dish.getBasePortion();
                    dishService.updatePriceForDishPortion(basePortion, newPrice);
                    if (dish.getActions() == null) dish.setActions(new ArrayList<>());
                    dish.getActions().add(action);
                });

        actionDao.save(action);
        return action;
    }
}