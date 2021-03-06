package ru.bironix.super_food.store.db.dao.dish;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.bironix.super_food.store.db.models.dish.Addon;
import ru.bironix.super_food.store.db.models.dish.Category;
import ru.bironix.super_food.store.db.models.dish.Dish;

import java.util.List;

@Repository
public interface CategoryDao extends JpaRepository<Category, String> {
}
