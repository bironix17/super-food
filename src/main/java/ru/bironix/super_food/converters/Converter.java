package ru.bironix.super_food.converters;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.bironix.super_food.db.models.action.Action;
import ru.bironix.super_food.db.models.common.PicturePaths;
import ru.bironix.super_food.db.models.dish.Addon;
import ru.bironix.super_food.db.models.dish.Dish;
import ru.bironix.super_food.db.models.dish.Portion;
import ru.bironix.super_food.db.models.dish.Price;
import ru.bironix.super_food.db.models.order.Order;
import ru.bironix.super_food.db.models.order.Status;
import ru.bironix.super_food.db.models.order.WayToGet;
import ru.bironix.super_food.db.models.person.Address;
import ru.bironix.super_food.db.models.person.Favorite;
import ru.bironix.super_food.db.models.person.Person;
import ru.bironix.super_food.dtos.request.AuthRequestDto;
import ru.bironix.super_food.dtos.common.PicturePathsDto;
import ru.bironix.super_food.dtos.action.FullActionDto;
import ru.bironix.super_food.dtos.action.SmallActionDto;
import ru.bironix.super_food.dtos.dish.*;
import ru.bironix.super_food.dtos.order.OrderDto;
import ru.bironix.super_food.dtos.order.StatusDto;
import ru.bironix.super_food.dtos.order.WayToGetDto;
import ru.bironix.super_food.dtos.person.AddressDto;
import ru.bironix.super_food.dtos.person.PersonDto;
import ru.bironix.super_food.dtos.request.createOrder.*;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
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

    Addon fromDto(AddonRequestDto addonDto);

    @Mapping(target="priceNow", source="portionDto.price")
    Portion fromDto(PortionRequestDto portionDto);

    Price fromDto(PriceRequestDto priceDto);

    Address fromDto(AddressRequestDto addressDto);

    @Mapping(target="basePortion", source="dishDto.portion")
    Dish fromDto(DishRequestDto dishDto);

    WayToGet fromDto(WayToGetDto status);

    Order fromDto(OrderRequestDto orderDto);

    default Integer toDto(Favorite favorite){
        return favorite.getDishId();
    }

    @Mapping(target="dishId", source="favorite")
    Favorite fromDto(Integer favorite);


    default List<SmallActionDto> toActionsDto(List<Action> actions) {
        return actions.stream()
                .map(this::toSmallDto)
                .collect(toList());
    }

    default List<CategoryDto> toCategoriesDto(List<Dish> dishes) {
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

    default LocalDateTime toLocalDateTime(Date date) {
        return date.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime();
    }

    default Date toDate(LocalDateTime localDateTime) {
        return java.sql.Timestamp.valueOf(localDateTime);
    }

    default List<Integer> toFavoritesDto(List<Favorite> favorites){
        return favorites.stream()
                .map(Favorite::getDishId)
                .collect(toList());
    }
}