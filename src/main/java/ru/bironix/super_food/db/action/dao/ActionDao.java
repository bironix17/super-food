
package ru.bironix.super_food.db.action.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.bironix.super_food.db.action.models.Action;
import ru.bironix.super_food.db.dish.models.Dish;

@Repository
public interface ActionDao extends CrudRepository<Action, Integer> {
}
