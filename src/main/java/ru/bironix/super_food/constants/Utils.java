package ru.bironix.super_food.constants;

import ru.bironix.super_food.dtos.common.PicturePathsDto;

//TODO убрать
public class Utils {

    public static PicturePathsDto getMockPicturesDto() {
        return new PicturePathsDto("https://friendfunction.ru/upload/resize_cache/iblock/8c4/600_600_13a38aab6457ec8a192e49fcdfc20568a/8c408497cf5c337bcba9b69412ee4e8b.jpg");
    }

//    public static DishDto.Base.Small getMockSmallDishDto(int id, CategoryTypeDto categoryTypeDto) {
//        return DishDto.Base.Small.builder()
//                .id(id)
//                .picturePaths(Utils.getMockPicturesDto())
//                .name("Утка")
//                .basePortion(PortionDto.builder()
//                        .id(0)
//                        .size("1 кг")
//                        .priceNow(PriceDto.builder()
//                                .id(0)
//                                .price(100)
//                                .build()
//                        )
//                        .oldPrice(PriceDto.builder()
//                                .id(1)
//                                .price(120)
//                                .build()
//                        )
//                        .build())
//                .category(categoryTypeDto)
//                .composition("нога, клешня, хурма")
//                .build();
//    }


//    public static PersonDto getMockUser() {
//        return PersonDto.builder()
//                .id(0)
//                .name("Виктори")
//                .email("rnosov@sfedu.ru")
//                .password("katia4size")
//                .addresses(List.of(new AddressDto(null, "Москва, ква ква")))
//                .build();
//    }
}
