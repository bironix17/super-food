package ru.bironix.super_food.converters;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import ru.bironix.super_food.db.models.Action;
import ru.bironix.super_food.db.models.PicturePaths;
import ru.bironix.super_food.db.models.dish.*;
import ru.bironix.super_food.db.models.order.Order;
import ru.bironix.super_food.db.models.order.Status;
import ru.bironix.super_food.db.models.order.WayToGet;
import ru.bironix.super_food.db.models.person.Address;
import ru.bironix.super_food.db.models.person.Person;
import ru.bironix.super_food.dtos.AddressDto;
import ru.bironix.super_food.dtos.PersonDto;
import ru.bironix.super_food.dtos.PicturePathsDto;
import ru.bironix.super_food.dtos.action.FullActionDto;
import ru.bironix.super_food.dtos.action.SmallActionDto;
import ru.bironix.super_food.dtos.dish.*;
import ru.bironix.super_food.dtos.order.OrderDto;
import ru.bironix.super_food.dtos.order.StatusDto;
import ru.bironix.super_food.dtos.order.WayToGetDto;
import ru.bironix.super_food.dtos.request.createOrder.*;
import ru.bironix.super_food.dtos.responses.ActionsResponseDto;
import ru.bironix.super_food.dtos.responses.DishesInCategoriesResponseDto;
import ru.bironix.super_food.dtos.responses.DishesResponseDto;
import ru.bironix.super_food.dtos.responses.OrdersResponseDto;

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

    Order fromDto(OrderDto orderDto);

    OrderDto toDto(Order order);

    StatusDto toDto(Status status);

    Status fromDto(StatusDto status);




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


    default List<SmallDishDto> toDishes(List<Dish> dishes) {
        return dishes.stream()
                .map(this::toSmallDto)
                .collect(Collectors.toList());
    }


    default OrdersResponseDto toOrdersResponse(List<Order> orders) {
        var ordersDtos = orders.stream()
                .map(this::toDto)
                .collect(Collectors.toList());

        return new OrdersResponseDto(ordersDtos);
    }


    Addon fromDto(AddonRequestDto addonDto);

    @Mapping(target="priceNow", source="portionDto.price")
    Portion fromDto(PortionRequestDto portionDto);

    Price fromDto(PriceRequestDto priceDto);

    Address fromDto(AddressRequestDto addressDto);

    @Mapping(target="basePortion", source="dishDto.portion")
    Dish fromDto(DishRequestDto dishDto);

    WayToGet fromDto(WayToGetDto status);

    Order fromDto(OrderRequestDto orderDto);
}