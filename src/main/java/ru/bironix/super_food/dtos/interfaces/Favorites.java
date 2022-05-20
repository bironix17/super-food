package ru.bironix.super_food.dtos.interfaces;

import ru.bironix.super_food.dtos.person.FavoriteDto;

import java.util.List;

public interface Favorites {
    List<FavoriteDto> getFavorites();
}
