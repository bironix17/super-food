package ru.bironix.super_food.services;

import org.apache.commons.collections4.IteratorUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.bironix.super_food.db.dao.action.ActionDao;
import ru.bironix.super_food.db.models.action.Action;
import ru.bironix.super_food.exceptions.NotFoundSourceException;

import java.util.ArrayList;
import java.util.List;

@Service
public class ActionService {

    private final ActionDao actionDao;
    private final DishService dishService;

    @Autowired
    public ActionService(ActionDao actionDao, DishService dishService) {
        this.actionDao = actionDao;
        this.dishService = dishService;
    }


    public List<Action> getActions() {
        return IteratorUtils.toList(actionDao.findAll().iterator());
    }

    public Action getAction(int id) {
        return actionDao.findById(id).orElseThrow(() -> new NotFoundSourceException(id, "Action"));
    }

    public Action createAction(Action action, int newPrice) {
        action.getDishes()
                .forEach(dish -> {
                    var basePortion = dish.getBasePortion();
                    dishService.updatePriceForDishPortion(basePortion, newPrice);
                });

        actionDao.save(action);
        return action;
    }
}