package ru.bironix.super_food.db.dish.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.bironix.super_food.db.dish.models.Addon;
import ru.bironix.super_food.db.dish.models.Price;

//TODO изучить, можно ли перенести в DishDao
@Repository
public interface PriceDao extends CrudRepository<Price, Integer> {
}
