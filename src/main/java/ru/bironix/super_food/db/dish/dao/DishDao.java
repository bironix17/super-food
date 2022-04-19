package ru.bironix.super_food.db.dish.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.bironix.super_food.db.dish.models.Addon;
import ru.bironix.super_food.db.dish.models.Dish;

import java.util.List;

@Repository
public interface DishDao extends CrudRepository<Dish, Integer> {
    List<Dish> findAllByAddonsContains(Addon addon);
}
