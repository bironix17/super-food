package ru.bironix.super_food.services;

import org.springframework.stereotype.Service;
import ru.bironix.super_food.models.CategoryDto;
import ru.bironix.super_food.models.PortionDto;
import ru.bironix.super_food.models.dish.FullDishDto;
import ru.bironix.super_food.models.PicturePathsDto;
import ru.bironix.super_food.models.dish.SmallDishDto;

import java.util.List;

@Service
public class DishService {

    public FullDishDto getFullDish(int id) {
        return FullDishDto.builder()
                .id(0)
                .picturePaths(new PicturePathsDto("https://friendfunction.ru/upload/resize_cache/iblock/8c4/600_600_13a38aab6457ec8a192e49fcdfc20568a/8c408497cf5c337bcba9b69412ee4e8b.jpg"))
                .name("Утка")
                .description("Уткастая утка")
                .composition("нога, нога, крыло, крыло, голова")
                .allergens("Перья")
                .baseIndexPortion(1)
                .category(CategoryDto.BURGERS)
                .portions(List.of(PortionDto.builder()
                                .id(0)
                                .price(100)
                                .size("1 килограмм")
                                .build(),
                        PortionDto.builder()
                                .id(0)
                                .price(199)
                                .size("2 килограмма")
                                .build()))
                .build();
    }

    public List<SmallDishDto> getDishes() {

        return List.of(SmallDishDto.builder()
                        .id(0)
                        .picturePaths(new PicturePathsDto("https://friendfunction.ru/upload/resize_cache/iblock/8c4/600_600_13a38aab6457ec8a192e49fcdfc20568a/8c408497cf5c337bcba9b69412ee4e8b.jpg"))
                        .name("Утка")
                        .category(CategoryDto.BURGERS)
                        .description("Уткастая утка")
                        .build(),
                SmallDishDto.builder()
                        .id(1)
                        .picturePaths(new PicturePathsDto("https://friendfunction.ru/upload/resize_cache/iblock/8c4/600_600_13a38aab6457ec8a192e49fcdfc20568a/8c408497cf5c337bcba9b69412ee4e8b.jpg"))
                        .category(CategoryDto.PIZZA)
                        .name("Утка")
                        .description("Уткастая утка")
                        .build());

    }
}
