package ru.bironix.super_food;

import ru.bironix.super_food.models.PicturePathsDto;
import ru.bironix.super_food.models.UserDto;
import ru.bironix.super_food.models.dish.CategoryType;
import ru.bironix.super_food.models.dish.PortionDto;
import ru.bironix.super_food.models.dish.SmallDishDto;

import java.util.List;

public class Utils {

    //TODO
    public static PicturePathsDto getMockPicturesDto() {
        return new PicturePathsDto("https://friendfunction.ru/upload/resize_cache/iblock/8c4/600_600_13a38aab6457ec8a192e49fcdfc20568a/8c408497cf5c337bcba9b69412ee4e8b.jpg");
    }

    public static SmallDishDto getMockSmallDishDto(int id, CategoryType categoryType) {
        return SmallDishDto.builder()
                .id(id)
                .picturePaths(Utils.getMockPicturesDto())
                .name("Утка")
                .basePortion(PortionDto.builder()
                        .id(0)
                        .size("1 кг")
                        .price(100)
                        .build())
                .category(categoryType)
                .composition("нога, клешня, хурма")
                .build();
    }

    public static SmallDishDto getMockSmallDishComboDto(int id) {
        return SmallDishDto.builder()
                .id(id)
                .picturePaths(Utils.getMockPicturesDto())
                .name("Утка")
                .basePortion(PortionDto.builder()
                        .id(0)
                        .size("2 упаковки")
                        .price(100)
                        .build())
                .category(CategoryType.COMBO)
                .composition("нога, клешня, хурма")
                .build();
    }

    public static UserDto getMockUser() {
        return UserDto.builder()
                .id(0)
                .name("Виктори")
                .email("rnosov@sfedu.ru")
                .password("katia4size")
                .addresses(List.of("Москва, ква ква"))
                .build();
    }
}
