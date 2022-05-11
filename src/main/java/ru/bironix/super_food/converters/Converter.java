package ru.bironix.super_food.converters;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.bironix.super_food.db.models.action.Action;
import ru.bironix.super_food.db.models.common.PicturePaths;
import ru.bironix.super_food.db.models.dish.*;
import ru.bironix.super_food.db.models.order.Order;
import ru.bironix.super_food.db.models.order.OrderStatus;
import ru.bironix.super_food.db.models.order.WayToGet;
import ru.bironix.super_food.db.models.person.Address;
import ru.bironix.super_food.db.models.person.Favorite;
import ru.bironix.super_food.db.models.person.Person;
import ru.bironix.super_food.dtos.action.ActionDto;
import ru.bironix.super_food.dtos.common.PicturePathsDto;
import ru.bironix.super_food.dtos.dish.*;
import ru.bironix.super_food.dtos.order.OrderDto;
import ru.bironix.super_food.dtos.order.OrderStatusDto;
import ru.bironix.super_food.dtos.order.WayToGetDto;
import ru.bironix.super_food.dtos.person.AddressDto;
import ru.bironix.super_food.dtos.person.PersonDto;
import ru.bironix.super_food.dtos.AuthRequestDto;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

@Mapper(componentModel = "spring")
public interface Converter {

    PicturePathsDto toDto(PicturePaths picturePaths);
    PicturePaths fromDto(PicturePathsDto picturePathsDto);

    PortionDto.Base toDto(Portion portion);
    @Mapping(target="price", source="portion.priceNow")
    PortionDto.Bind toBindDto(Portion portion);
    @Mapping(target="price", source="portion.priceNow")
    PortionDto.CreateUpdateForAction toCreateUpdateForActionDto(Portion portion);
    Portion fromDto(PortionDto.CreateUpdate portionDto);
    Portion fromDto(PortionDto.Base portionDto);
    @Mapping(target="priceNow", source="portionDto.price")
    Portion fromDto(PortionDto.Bind portionDto);
    @Mapping(target="priceNow", source="portionDto.price")
    Portion fromDto(PortionDto.CreateUpdateForAction portionDto);

    PriceDto.Base toDto(Price price);
    Price fromDto(PriceDto.CreateUpdate priceDto);
    Price fromDto(PriceDto.Base priceDto);
    Price fromDto(PriceDto.Bind priceDto);

    PersonDto.Base toDto(Person user);
    Person toPerson(AuthRequestDto request);
    Person fromDto(PersonDto.Create personDto);
    Person fromDto(PersonDto.Update personDto);
    Person fromDto(PersonDto.Base personDto);
    Person fromDto(PersonDto.Bind personDto);

    AddressDto toDto(Address address);
    Address fromDto(AddressDto addressDto);

    DishDto.Base.Full toFullDto(Dish dish);
    DishDto.Base.Small toSmallDto(Dish dish);
    @Mapping(target="portion", source="dish.basePortion")
    DishDto.Bind toDto(Dish dish);
    Dish fromDto(DishDto.CreateUpdate dishDto);
    Dish fromDto(DishDto.Base.Full dishDto);
    Dish fromDto(DishDto.Base.Small dishDto);
    @Mapping(target="basePortion", source="dishDto.portion")
    Dish fromDto(DishDto.Bind dishDto);

    DishCountDto toDto(DishCount dishesCount);
    DishCount fromDto(DishCountDto dishCountDto);


    ActionDto.Base.Small toSmallDto(Action action);
    ActionDto.Base.Full toFullDto(Action action);

    Action fromDto(ActionDto.CreateUpdate actionDto);
    Action fromDto(ActionDto.Base.Small actionDto);
    Action fromDto(ActionDto.Base.Full actionDto);

    OrderDto.Base.Small toSmallDto(Order order);
    OrderDto.Base.Full toFullDto(Order order);
    Order fromDto(OrderDto.CreateUpdate orderDto);
    Order fromDto(OrderDto.Base.Small orderDto);
    Order fromDto(OrderDto.Base.Full orderDto);


    OrderStatusDto toDto(OrderStatus orderStatus);
    OrderStatus fromDto(OrderStatusDto status);

    AddonDto.Base toDto(Addon addon);
    Addon fromDto(AddonDto.CreateUpdate addonDto);
    Addon fromDto(AddonDto.Base addonDto);
    Addon fromDto(AddonDto.Bind addonDto);


    WayToGetDto fromDto(WayToGet wayToGet);
    WayToGet fromDto(WayToGetDto wayToGetDto);

    default Integer toDto(Favorite favorite){
        return favorite.getDishId();
    }

    @Mapping(target="dishId", source="favorite")
    Favorite fromDto(Integer favorite);


    default List<ActionDto.Base.Small> toActionsDto(List<Action> actions) {
        return actions.stream()
                .map(this::toSmallDto)
                .collect(toList());
    }

    default List<CategoryDto> toCategoriesDto(List<Dish> dishes) {
        return dishes.stream()
                .map(this::toSmallDto)
                .collect(Collectors.groupingBy(DishDto.Base.Small::getCategory))
                .entrySet().stream()
                .map(i -> new CategoryDto(i.getKey(), i.getValue()))
                .collect(Collectors.toList());
    }


    default List<DishDto.Base.Small> toDishesDto(List<Dish> dishes) {
        return dishes.stream()
                .map(this::toSmallDto)
                .collect(Collectors.toList());
    }


    default List<OrderDto.Base.Small> toOrdersDto(List<Order> orders) {
        return orders.stream()
                .map(this::toSmallDto)
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