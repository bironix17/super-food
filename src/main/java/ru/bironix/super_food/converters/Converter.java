package ru.bironix.super_food.converters;

import org.mapstruct.Mapper;
import ru.bironix.super_food.db.models.Action;
import ru.bironix.super_food.db.models.PicturePaths;
import ru.bironix.super_food.db.models.dish.*;
import ru.bironix.super_food.db.models.order.Order;
import ru.bironix.super_food.db.models.order.Status;
import ru.bironix.super_food.db.models.person.Address;
import ru.bironix.super_food.db.models.person.Person;
import ru.bironix.super_food.dtos.person.AddressDto;
import ru.bironix.super_food.dtos.AuthRequestDto;
import ru.bironix.super_food.dtos.person.PersonDto;
import ru.bironix.super_food.dtos.PicturePathsDto;
import ru.bironix.super_food.dtos.action.FullActionDto;
import ru.bironix.super_food.dtos.action.SmallActionDto;
import ru.bironix.super_food.dtos.dish.*;
import ru.bironix.super_food.dtos.order.OrderDto;
import ru.bironix.super_food.dtos.order.StatusDto;

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

    Person toPerson(AuthRequestDto request);

    default List<SmallActionDto> toActionsDto(List<Action> actions) {
        return actions.stream()
                .map(this::toSmallDto)
                .collect(toList());

    }

    default List<CategoryDto> toCategoryDto(List<Dish> dishes) {
        return dishes.stream()
                .map(this::toSmallDto)
                .collect(Collectors.groupingBy(AbstractDishDto::getCategory))
                .entrySet().stream()
                .map(i -> new CategoryDto(i.getKey(), i.getValue()))
                .collect(Collectors.toList());
    }


    default List<SmallDishDto> toDishesDto(List<Dish> dishes) {
        return dishes.stream()
                .map(this::toSmallDto)
                .collect(Collectors.toList());
    }


    default List<OrderDto> toOrdersDto(List<Order> orders) {
        return orders.stream()
                .map(this::toDto)
                .collect(Collectors.toList());

    }
}