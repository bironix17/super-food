package ru.bironix.super_food.store.db.dao.person;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.bironix.super_food.store.db.models.person.Favorite;

public interface FavoritesDao extends JpaRepository<Favorite, Integer> {
}
