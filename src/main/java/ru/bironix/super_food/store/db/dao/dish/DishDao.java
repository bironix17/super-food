package ru.bironix.super_food.store.db.dao.dish;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.bironix.super_food.store.db.models.dish.Addon;
import ru.bironix.super_food.store.db.models.dish.Dish;
import ru.bironix.super_food.store.db.models.dish.Portion;

import java.util.Collection;
import java.util.List;

@Repository
public interface DishDao extends JpaRepository<Dish, Integer> {
    List<Dish> findAllByAddonsContains(Addon addon);

    List<Dish> findByDeleted(Boolean deleted);

    List<Dish> findByPortionsIsIn(Collection<Portion> portions);


}
