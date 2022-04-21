
package ru.bironix.super_food.db.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.bironix.super_food.db.models.Action;

@Repository
public interface ActionDao extends CrudRepository<Action, Integer> {
}
