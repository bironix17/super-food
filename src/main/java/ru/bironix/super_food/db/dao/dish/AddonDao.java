package ru.bironix.super_food.db.dao.dish;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.bironix.super_food.db.models.dish.Addon;

@Repository
public interface AddonDao extends CrudRepository<Addon, Integer> {
}