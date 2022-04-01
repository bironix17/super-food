package ru.bironix.super_food.services;

import org.springframework.stereotype.Service;
import ru.bironix.super_food.Utils;
import ru.bironix.super_food.models.CategoryDto;
import ru.bironix.super_food.models.TempDto;
import ru.bironix.super_food.models.dish.CategoryTypeDto;
import ru.bironix.super_food.models.dish.PortionDto;
import ru.bironix.super_food.models.dish.FullDishDto;
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
                .category(CategoryTypeDto.BURGERS)
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
                        .category(CategoryTypeDto.BURGERS)
                        .description("Уткастая утка")
                        .build(),
                SmallDishDto.builder()
                        .id(1)
                        .picturePaths(Utils.getMockPicturesDto())
                        .basePortion(PortionDto.builder()
                                .id(2)
                                .size("10 см")
                                .price(200)
                                .build())
                        .category(CategoryTypeDto.PIZZA)
                        .name("Утка")
                        .description("Уткастая утка")
                        .build());

    }

    public TempDto getCategories() {
        return TempDto.builder().categories(List.of(
                CategoryDto.builder()
                        .categoryType(CategoryTypeDto.BURGERS)
                        .dishes(
                                List.of(
                                        SmallDishDto.builder()
                                                .id(3)
                                                .picturePaths(Utils.getMockPicturesDto())
                                                .name("Утка")
                                                .basePortion(PortionDto.builder()
                                                        .id(0)
                                                        .size("1 кг")
                                                        .price(100)
                                                        .build())
                                                .category(CategoryTypeDto.BURGERS)
                                                .description("Уткастая утка")
                                                .build(),
                                        SmallDishDto.builder()
                                                .id(4)
                                                .picturePaths(Utils.getMockPicturesDto())
                                                .basePortion(PortionDto.builder()
                                                        .id(1)
                                                        .size("10 см")
                                                        .price(200)
                                                        .build())
                                                .category(CategoryTypeDto.BURGERS)
                                                .name("Утка")
                                                .description("Уткастая утка")
                                                .build(),
                                        SmallDishDto.builder()
                                                .id(5)
                                                .picturePaths(Utils.getMockPicturesDto())
                                                .basePortion(PortionDto.builder()
                                                        .id(1)
                                                        .size("10 см")
                                                        .price(200)
                                                        .build())
                                                .category(CategoryTypeDto.BURGERS)
                                                .name("Утка")
                                                .description("Уткастая утка")
                                                .build())
                        ).build(),


                CategoryDto.builder()
                        .categoryType(CategoryTypeDto.PIZZA)
                        .dishes(
                                List.of(
                                        SmallDishDto.builder()
                                                .id(6)
                                                .picturePaths(Utils.getMockPicturesDto())
                                                .name("Утка")
                                                .basePortion(PortionDto.builder()
                                                        .id(0)
                                                        .size("1 кг")
                                                        .price(100)
                                                        .build())
                                                .category(CategoryTypeDto.PIZZA)
                                                .description("Уткастая утка")
                                                .build(),
                                        SmallDishDto.builder()
                                                .id(7)
                                                .picturePaths(Utils.getMockPicturesDto())
                                                .basePortion(PortionDto.builder()
                                                        .id(1)
                                                        .size("10 см")
                                                        .price(200)
                                                        .build())
                                                .category(CategoryTypeDto.PIZZA)
                                                .name("Утка")
                                                .description("Уткастая утка")
                                                .build(),
                                        SmallDishDto.builder()
                                                .id(8)
                                                .picturePaths(Utils.getMockPicturesDto())
                                                .basePortion(PortionDto.builder()
                                                        .id(1)
                                                        .size("10 см")
                                                        .price(200)
                                                        .build())
                                                .category(CategoryTypeDto.PIZZA)
                                                .name("Утка")
                                                .description("Уткастая утка")
                                                .build())
                        ).build(),

                CategoryDto.builder()
                        .categoryType(CategoryTypeDto.ROLLS)
                        .dishes(
                                List.of(
                                        SmallDishDto.builder()
                                                .id(9)
                                                .picturePaths(Utils.getMockPicturesDto())
                                                .name("Утка")
                                                .basePortion(PortionDto.builder()
                                                        .id(0)
                                                        .size("1 кг")
                                                        .price(100)
                                                        .build())
                                                .category(CategoryTypeDto.ROLLS)
                                                .description("Уткастая утка")
                                                .build(),
                                        SmallDishDto.builder()
                                                .id(10)
                                                .picturePaths(Utils.getMockPicturesDto())
                                                .basePortion(PortionDto.builder()
                                                        .id(1)
                                                        .size("10 см")
                                                        .price(200)
                                                        .build())
                                                .category(CategoryTypeDto.ROLLS)
                                                .name("Утка")
                                                .description("Уткастая утка")
                                                .build(),
                                        SmallDishDto.builder()
                                                .id(11)
                                                .picturePaths(Utils.getMockPicturesDto())
                                                .basePortion(PortionDto.builder()
                                                        .id(1)
                                                        .size("10 см")
                                                        .price(200)
                                                        .build())
                                                .category(CategoryTypeDto.ROLLS)
                                                .name("Утка")
                                                .description("Уткастая утка")
                                                .build())
                        ).build()
        )).build();
    }
}
