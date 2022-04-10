package ru.bironix.super_food.converters;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import ru.bironix.super_food.db.dish.models.*;
import ru.bironix.super_food.dtos.PicturePathsDto;
import ru.bironix.super_food.dtos.dish.*;
import ru.bironix.super_food.dtos.responses.DishesInCategoriesDto;

import java.util.List;

@Mapper(componentModel = "spring")
public interface DishConverter {

    AddonDto toDto(Addon addon);

    Addon fromDto(AddonDto addonDto);


    PicturePathsDto toDto(PicturePaths picturePaths);

    PicturePaths fromDto(PicturePathsDto picturePathsDto);


    PortionDto toDto(Portion portion);

    Portion fromDto(PortionDto portionDto);


    PriceDto toDto(Price price);

    Price fromDto(PriceDto priceDto);

    FullDishDto toFullDishDto(Dish addon);

    Dish fromFullDishDto(FullDishDto addonDto);

    @Mapping(target = "basePortion", source = "dish", qualifiedByName= "toSmallDishDtoBasePortion")
    SmallDishDto toSmallDishDto(Dish dish);

    Dish fromSmallDishDto(SmallDishDto dishDto);

    @Mapping(target = "categories", source = "categories") //TODO не работает аннотация
    default DishesInCategoriesDto toDishesInCategoriesDto(List<CategoryDto> categories) {
        return new DishesInCategoriesDto(categories);
    }

    @Named("toSmallDishDtoBasePortion")
    default PortionDto toSmallDishDtoBasePortion(Dish dish) {
        var portion = dish.getPortions().get(dish.getBaseIndexPortion());
        return toDto(portion);
    }
}
