package ru.bironix.super_food.services;

import org.springframework.stereotype.Service;
import ru.bironix.super_food.Utils;
import ru.bironix.super_food.models.responses.DishesInCategoriesDto;
import ru.bironix.super_food.models.dish.CategoryDto;
import ru.bironix.super_food.models.dish.CategoryType;
import ru.bironix.super_food.models.dish.FullDishDto;
import ru.bironix.super_food.models.dish.PortionDto;

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
                .category(CategoryType.BURGERS)
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

    public DishesInCategoriesDto getCategories() {
        return DishesInCategoriesDto.builder().categories(List.of(
                CategoryDto.builder()
                        .categoryType(CategoryType.BURGERS)
                        .dishes(
                                List.of(
                                        Utils.getMockSmallDishDto(0, CategoryType.BURGERS),
                                        Utils.getMockSmallDishDto(1, CategoryType.BURGERS),
                                        Utils.getMockSmallDishDto(2, CategoryType.BURGERS))
                        ).build(),

                CategoryDto.builder()
                        .categoryType(CategoryType.PIZZA)
                        .dishes(
                                List.of(
                                        Utils.getMockSmallDishDto(3, CategoryType.PIZZA),
                                        Utils.getMockSmallDishDto(4, CategoryType.PIZZA),
                                        Utils.getMockSmallDishDto(5, CategoryType.PIZZA))
                        ).build(),


                CategoryDto.builder()
                        .categoryType(CategoryType.ROLLS)
                        .dishes(
                                List.of(
                                        Utils.getMockSmallDishDto(6, CategoryType.ROLLS),
                                        Utils.getMockSmallDishDto(7, CategoryType.ROLLS),
                                        Utils.getMockSmallDishDto(8, CategoryType.ROLLS))
                        ).build(),

                CategoryDto.builder()
                        .categoryType(CategoryType.COMBO)
                        .dishes(
                                List.of(
                                        Utils.getMockSmallDishComboDto(9),
                                        Utils.getMockSmallDishComboDto(10),
                                        Utils.getMockSmallDishComboDto(11)
                                        )
                        ).build()
        )).build();
    }
}
