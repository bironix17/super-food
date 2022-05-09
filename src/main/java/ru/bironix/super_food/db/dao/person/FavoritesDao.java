package ru.bironix.super_food.db.dao.person;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.bironix.super_food.db.models.person.Favorite;

public interface FavoritesDao extends JpaRepository<Favorite, Integer> {
}
