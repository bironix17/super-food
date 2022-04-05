package ru.bironix.super_food.db.dish.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.bironix.super_food.db.dish.models.Dish;

@Repository
public interface DishDao extends CrudRepository<Dish, Integer> {
}
