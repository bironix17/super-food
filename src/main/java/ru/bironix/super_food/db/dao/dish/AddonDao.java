package ru.bironix.super_food.db.dao.dish;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.bironix.super_food.db.models.dish.Addon;

@Repository
public interface AddonDao extends JpaRepository<Addon, Integer> {
}