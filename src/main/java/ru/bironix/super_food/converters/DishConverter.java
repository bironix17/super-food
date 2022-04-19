package ru.bironix.super_food.converters;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import ru.bironix.super_food.db.action.models.Action;
import ru.bironix.super_food.db.dish.models.*;
import ru.bironix.super_food.db.generalModels.PicturePaths;
import ru.bironix.super_food.dtos.PicturePathsDto;
import ru.bironix.super_food.dtos.action.FullActionDto;
import ru.bironix.super_food.dtos.action.SmallActionDto;
import ru.bironix.super_food.dtos.dish.*;
import ru.bironix.super_food.dtos.responses.ActionsResponseDto;
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

//    @Mapping(target = "basePortion", source = "dish", qualifiedByName = "toSmallDishDtoBasePortion")
    SmallDishDto toSmallDishDto(Dish dish);

//    @Mapping(target = "portions", source = "basePortion", qualifiedByName = "toDishBasePortion")
    Dish fromSmallDishDto(SmallDishDto dishDto);

    @Mapping(target = "categories", source = "categories") //TODO не работает аннотация
    default DishesInCategoriesDto toDishesInCategoriesDto(List<CategoryDto> categories) {
        return new DishesInCategoriesDto(categories);
    }

//    @Named("toSmallDishDtoBasePortion")
//    default PortionDto toSmallDishDtoBasePortion(Dish dish) {
//        var portion = dish.getPortions().stream()
//                .filter(p -> p.getId() == dish.getBasePortionId())
//                .findAny().orElse(null);
//        return toDto(portion);
//    }

//    @Named("toDishBasePortion")
//    default List<Portion> toDishBasePortion(PortionDto portionDto) {
//        return List.of(fromDto(portionDto));
//    }


    SmallActionDto toSmallDto(Action action);

    FullActionDto toFullDto(Action action);

    Action fromFullDto(FullActionDto actionDto);


    @Mapping(target = "categories", source = "categories")
    default ActionsResponseDto toActionsResponseDto(List<SmallActionDto> actionDtos) {
        return new ActionsResponseDto(actionDtos);
    }

}
