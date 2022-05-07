package ru.bironix.super_food.db.dao.person;

import org.springframework.data.repository.CrudRepository;
import ru.bironix.super_food.db.models.person.Favorite;

public interface FavoritesDao extends CrudRepository<Favorite, Integer> {
}
