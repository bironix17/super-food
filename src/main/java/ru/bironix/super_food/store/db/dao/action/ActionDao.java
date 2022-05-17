
package ru.bironix.super_food.store.db.dao.action;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.bironix.super_food.store.db.models.action.Action;
import ru.bironix.super_food.store.db.models.dish.Dish;

import java.util.Collection;
import java.util.List;

@Repository
public interface ActionDao extends JpaRepository<Action, Integer> {
    long countByDishesIn(Collection<Dish> dishes);


}
