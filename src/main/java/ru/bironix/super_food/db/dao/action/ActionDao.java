
package ru.bironix.super_food.db.dao.action;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.bironix.super_food.db.models.action.Action;

@Repository
public interface ActionDao extends JpaRepository<Action, Integer> {
}
