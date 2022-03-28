package ru.bironix.super_food.services;

import org.springframework.stereotype.Service;
import ru.bironix.super_food.Utils;
import ru.bironix.super_food.models.dish.CategoryDto;
import ru.bironix.super_food.models.dish.PortionDto;
import ru.bironix.super_food.models.dish.FullDishDto;
import ru.bironix.super_food.models.PicturePathsDto;
import ru.bironix.super_food.models.dish.SmallDishDto;

import java.util.List;

@Service
public class DishService {

    public FullDishDto getFullDish(int id) {
        return FullDishDto.builder()
                .id(0)
                .picturePaths(Utils.getMockPicturesDto())
                .name("Утка")
                .description("Уткастая утка")
                .composition("нога, нога, крыло, крыло, голова")
                .allergens("Перья")
                .baseIndexPortion(1)
                .category(CategoryDto.BURGERS)
                .portions(List.of(PortionDto.builder()
                                .id(id)
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
                        .picturePaths(Utils.getMockPicturesDto())
                        .name("Утка")
                        .basePortion(PortionDto.builder()
                                .id(0)
                                .size("1 кг")
                                .price(100)
                                .build())
                        .category(CategoryDto.BURGERS)
                        .description("Уткастая утка")
                        .build(),
                SmallDishDto.builder()
                        .id(1)
                        .picturePaths(Utils.getMockPicturesDto())
                        .basePortion(PortionDto.builder()
                                .id(1)
                                .size("10 см")
                                .price(200)
                                .build())
                        .category(CategoryDto.PIZZA)
                        .name("Утка")
                        .description("Уткастая утка")
                        .build());

    }
}
