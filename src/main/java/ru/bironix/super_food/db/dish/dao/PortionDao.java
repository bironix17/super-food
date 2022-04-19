package ru.bironix.super_food.db.dish.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.bironix.super_food.db.dish.models.Portion;

@Repository
public interface PortionDao extends CrudRepository<Portion, Integer> {
}
