package ru.bironix.super_food.services;

import org.apache.commons.collections4.IteratorUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.bironix.super_food.store.db.dao.action.ActionDao;
import ru.bironix.super_food.store.db.models.action.Action;
import ru.bironix.super_food.store.UpdateMapper;
import ru.bironix.super_food.exceptions.NotFoundSourceException;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.List;

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

        action.getPortions()
                .forEach(dishService::createNewPriceForAction);

        actionDao.saveAndFlush(action);
        entityManager.refresh(action);
        return action;
    }


    public Action updateAction(Action action) {
        var actionDb = getAction(action.getId());

        actionDb.getPortions().forEach(dishService::deleteNewPriceForAction);
        updateMapper.map(action, actionDb);
        actionDb.getPortions().forEach(dishService::createNewPriceForAction);

        return actionDb;
    }

    public void deleteAction(int id) {
        var action = getAction(id);
        action.getPortions().forEach(dishService::deleteNewPriceForAction);
        actionDao.delete(action);
    }
}