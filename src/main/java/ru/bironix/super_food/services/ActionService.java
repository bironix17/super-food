package ru.bironix.super_food.services;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.IteratorUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.bironix.super_food.constants.ApiError;
import ru.bironix.super_food.exceptions.ApiException;
import ru.bironix.super_food.exceptions.NotFoundSourceException;
import ru.bironix.super_food.store.UpdateMapper;
import ru.bironix.super_food.store.db.dao.action.ActionDao;
import ru.bironix.super_food.store.db.models.action.Action;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
public class ActionService {

    private final ActionDao actionDao;
    private final DishService dishService;
    private final EntityManager entityManager;
    private final UpdateMapper updateMapper;

    @Autowired
    public ActionService(ActionDao actionDao,
                         DishService dishService,
                         UpdateMapper updateMapper,
                         EntityManager entityManager) {
        this.actionDao = actionDao;
        this.dishService = dishService;
        this.entityManager = entityManager;
        this.updateMapper = updateMapper;
    }

    public List<Action> getActions() {
        return IteratorUtils.toList(actionDao.findAll().iterator());
    }

    public Action getAction(int id) {
        return actionDao.findById(id).orElseThrow(() -> new NotFoundSourceException(id, "Action"));
    }

    @Transactional
    public Action createAction(Action action) {
        var newAction = new Action(action);
        checkAction(action);
        newAction.setPortions(newAction.getPortions().stream()
                .map(dishService::createNewPriceForAction)
                .collect(toList()));

        actionDao.saveAndFlush(newAction);
        entityManager.refresh(newAction);
        return newAction;
    }

    private void checkAction(Action action) {
        if (actionDao.countByDishesIn(action.getDishes()) > 0)
            throw new ApiException(ApiError.DISH_IS_ALREADY_IN_THE_ACTION);
    }

    @Transactional
    public Action updateAction(Action action) {
        var actionDb = getAction(action.getId());

        if (CollectionUtils.isNotEmpty(action.getDishes())) {
            actionDb.getPortions().forEach(dishService::deleteNewPriceForAction);
        }

        updateMapper.map(action, actionDb);

        if (CollectionUtils.isNotEmpty(action.getDishes())) {
            actionDb.setPortions(actionDb.getPortions().stream()
                    .map(dishService::createNewPriceForAction)
                    .collect(toList()));
            entityManager.refresh(actionDb);
        }
        return actionDb;
    }

    public void deleteAction(int id) {
        var action = getAction(id);
        action.getPortions().forEach(dishService::deleteNewPriceForAction);
        actionDao.delete(action);
    }
}