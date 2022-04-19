package ru.bironix.super_food.services;

import org.apache.commons.collections4.IteratorUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.bironix.super_food.Utils;
import ru.bironix.super_food.db.action.dao.ActionDao;
import ru.bironix.super_food.db.action.models.Action;
import ru.bironix.super_food.dtos.action.FullActionDto;
import ru.bironix.super_food.dtos.action.SmallActionDto;
import ru.bironix.super_food.dtos.dish.CategoryType;
import ru.bironix.super_food.dtos.responses.ActionsResponseDto;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
public class ActionService {

    @Autowired
    ActionDao actionDao;

    @Autowired
    DishService dishService;

    public List<Action> getActions() {
        return IteratorUtils.toList(actionDao.findAll().iterator());
    }

    //TODO
    public Action getAction(int id) {
        return actionDao.findById(id).orElse(null);
    }


    public Action createAction(Action action, int newPrice) {
        action.getDishes()
                .forEach(dish -> {
                    var basePortion = dish.getPortions().get(dish.getBaseIndexPortion());
                    dishService.updatePriceForDishPortion(basePortion, newPrice);
                    if(dish.getActions() == null) dish.setActions(new ArrayList<>());
                    dish.getActions().add(action);
                });

        actionDao.save(action);
        return action;
    }
}