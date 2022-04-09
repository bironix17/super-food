package ru.bironix.super_food.converters;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import ru.bironix.super_food.db.dish.models.*;
import ru.bironix.super_food.dtos.PicturePathsDto;
import ru.bironix.super_food.dtos.dish.*;

@Mapper(componentModel = "spring")
public interface DishConverter {

    SmallDishDto toSmallDishDto(Dish dish);
    SmallDishDto toFullDishDto(Dish dish);
    Dish fromFullDishDto(FullDishDto dishDto);



    AddonDto toDto(Addon addon);
//    @Mappings({
//            @Mapping(target="employeeId", source="entity.id"),
//            @Mapping(target="employeeName", source="entity.name")
//    })
    Addon fromDto(AddonDto addonDto);


    PicturePathsDto toDto(PicturePaths picturePaths);
    PicturePaths fromDto(PicturePathsDto picturePathsDto);


    PortionDto toDto(Portion portion);
    Portion fromDto(PortionDto portionDto);


    PriceDto toDto(Price price);
    Price fromDto(PriceDto priceDto);
}
