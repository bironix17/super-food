package ru.bironix.super_food.services;

import org.springframework.stereotype.Service;
import ru.bironix.super_food.models.FullDishDto;
import ru.bironix.super_food.models.PicturePathsDto;

@Service
public class DishService {

    public FullDishDto getFullDish(int id) {
        //TODO
        return new FullDishDto(new PicturePathsDto("https://friendfunction.ru/upload/resize_cache/iblock/8c4/600_600_13a38aab6457ec8a192e49fcdfc20568a/8c408497cf5c337bcba9b69412ee4e8b.jpg"),
                "Утка", 100, "1 килограмм");
    }
}
