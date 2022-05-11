package ru.bironix.super_food.store.db.dao.dish;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.bironix.super_food.store.db.models.dish.Portion;

@Repository
public interface PortionDao extends JpaRepository<Portion, Integer> {
}
