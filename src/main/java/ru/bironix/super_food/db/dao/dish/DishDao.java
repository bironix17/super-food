package ru.bironix.super_food.db.dao.dish;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.bironix.super_food.db.models.dish.Addon;
import ru.bironix.super_food.db.models.dish.Dish;

import java.util.List;

@Repository
public interface DishDao extends JpaRepository<Dish, Integer> {
    List<Dish> findAllByAddonsContains(Addon addon);
}
