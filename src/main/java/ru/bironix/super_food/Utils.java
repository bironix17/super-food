package ru.bironix.super_food;

import ru.bironix.super_food.models.PicturePathsDto;
import ru.bironix.super_food.models.action.SmallActionDto;
import ru.bironix.super_food.models.dish.CategoryDto;
import ru.bironix.super_food.models.dish.PortionDto;
import ru.bironix.super_food.models.dish.SmallDishDto;

public class Utils {

    //TODO
    public static PicturePathsDto getMockPicturesDto() {
        return new PicturePathsDto("https://friendfunction.ru/upload/resize_cache/iblock/8c4/600_600_13a38aab6457ec8a192e49fcdfc20568a/8c408497cf5c337bcba9b69412ee4e8b.jpg");
    }

    public static SmallDishDto getMockSmallDishDto(int id) {
        return SmallDishDto.builder()
                .id(id)
                .picturePaths(Utils.getMockPicturesDto())
                .name("Утка")
                .basePortion(PortionDto.builder()
                        .id(0)
                        .size("1 кг")
                        .price(100)
                        .build())
                .category(CategoryDto.BURGERS)
                .description("Уткастая утка")
                .build();
    }
}
