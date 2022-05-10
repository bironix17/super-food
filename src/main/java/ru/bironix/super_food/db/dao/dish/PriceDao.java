package ru.bironix.super_food.db.dao.dish;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.bironix.super_food.db.models.dish.Price;

@Repository
public interface PriceDao extends JpaRepository<Price, Integer> {
}
