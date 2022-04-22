package ru.bironix.super_food.converters;

import org.mapstruct.Mapper;
import ru.bironix.super_food.db.models.Action;
import ru.bironix.super_food.db.models.dish.Addon;
import ru.bironix.super_food.db.models.dish.Dish;
import ru.bironix.super_food.db.models.dish.Portion;
import ru.bironix.super_food.db.models.dish.Price;
import ru.bironix.super_food.db.models.dish.PicturePaths;
import ru.bironix.super_food.db.models.person.Address;
import ru.bironix.super_food.db.models.person.Person;
import ru.bironix.super_food.dtos.AddressDto;
import ru.bironix.super_food.dtos.PersonDto;
import ru.bironix.super_food.dtos.PicturePathsDto;
import ru.bironix.super_food.dtos.action.FullActionDto;
import ru.bironix.super_food.dtos.action.SmallActionDto;
import ru.bironix.super_food.dtos.dish.*;
import ru.bironix.super_food.dtos.responses.ActionsResponseDto;
import ru.bironix.super_food.dtos.responses.DishesInCategoriesResponseDto;
import ru.bironix.super_food.dtos.responses.DishesResponseDto;

import java.util.List;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

@Mapper(componentModel = "spring")
public interface Converter {

    AddonDto toDto(Addon addon);

    Addon fromDto(AddonDto addonDto);

    PicturePathsDto toDto(PicturePaths picturePaths);

    PicturePaths fromDto(PicturePathsDto picturePathsDto);

    PortionDto toDto(Portion portion);

    Portion fromDto(PortionDto portionDto);

    PriceDto toDto(Price price);

    Price fromDto(PriceDto priceDto);

    PersonDto toDto(Person user);

    Person fromDto(PersonDto personDto);

    AddressDto toDto(Address address);

    Address fromDto(AddressDto addressDto);


    FullDishDto toFullDto(Dish addon);

    Dish fromFullDto(FullDishDto addonDto);

    SmallDishDto toSmallDto(Dish dish);

    Dish fromSmallDto(SmallDishDto dishDto);


    SmallActionDto toSmallDto(Action action);

    FullActionDto toFullDto(Action action);

    Action fromFullDto(FullActionDto actionDto);


    default ActionsResponseDto toActionsResponseDto(List<Action> actions) {
        var actionsDtos = actions.stream()
                .map(this::toSmallDto)
                .collect(toList());
        return new ActionsResponseDto(actionsDtos);
    }

    default DishesInCategoriesResponseDto toDishesInCategoriesResponseDto(List<Dish> dishes) {
        var categories = dishes.stream()
                .map(this::toSmallDto)
                .collect(Collectors.groupingBy(AbstractDishDto::getCategory))
                .entrySet().stream()
                .map(i -> new CategoryDto(i.getKey(), i.getValue()))
                .collect(Collectors.toList());

        return new DishesInCategoriesResponseDto(categories);
    }

    default DishesResponseDto toDishesResponseDto(List<Dish> dishes) {
        var dishesDtos = dishes.stream()
                .map(this::toSmallDto)
                .collect(Collectors.toList());

        return new DishesResponseDto(dishesDtos);
    }
}