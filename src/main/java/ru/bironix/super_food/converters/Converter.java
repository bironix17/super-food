package ru.bironix.super_food.converters;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;
import ru.bironix.super_food.dtos.AuthRequestDto;
import ru.bironix.super_food.dtos.DeliveryInformationDto;
import ru.bironix.super_food.dtos.action.ActionDto;
import ru.bironix.super_food.dtos.common.PicturePathsDto;
import ru.bironix.super_food.dtos.dish.*;
import ru.bironix.super_food.dtos.order.OrderDto;
import ru.bironix.super_food.dtos.order.OrderStatusDto;
import ru.bironix.super_food.dtos.order.PaymentMethodDto;
import ru.bironix.super_food.dtos.order.WayToGetDto;
import ru.bironix.super_food.dtos.person.AddressDto;
import ru.bironix.super_food.dtos.person.FavoriteDto;
import ru.bironix.super_food.dtos.person.PersonDto;
import ru.bironix.super_food.store.db.models.action.Action;
import ru.bironix.super_food.store.db.models.common.PicturePaths;
import ru.bironix.super_food.store.db.models.dish.*;
import ru.bironix.super_food.store.db.models.order.Order;
import ru.bironix.super_food.store.db.models.order.OrderStatus;
import ru.bironix.super_food.store.db.models.order.PaymentMethod;
import ru.bironix.super_food.store.db.models.order.WayToGet;
import ru.bironix.super_food.store.db.models.person.Address;
import ru.bironix.super_food.store.db.models.person.Favorite;
import ru.bironix.super_food.store.db.models.person.Person;
import ru.bironix.super_food.store.fileStore.models.DeliveryInformation;

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

    @Mapping(target = "price", source = "portion.priceNow")
    PortionDto.Bind toBindDto(Portion portion);
    @Mapping(target = "price", source = "portion.priceNow")
    PortionDto.CreateUpdateForAction toCreateUpdateForActionDto(Portion portion);
    Portion fromDto(PortionDto.CreateUpdate portionDto);
    Portion fromDto(PortionDto.Base portionDto);
    @Mapping(target = "priceNow", source = "portionDto.price")
    Portion fromDto(PortionDto.Bind portionDto);
    @Mapping(target = "priceNow", source = "portionDto.price")
    Portion fromDto(PortionDto.CreateUpdateForAction portionDto);

    PriceDto.Base toDto(Price price);
    PriceDto.Bind toBindDto(Price price);
    Price fromDto(PriceDto.CreateUpdate priceDto);
    Price fromDto(PriceDto.Base priceDto);
    Price fromDto(PriceDto.Bind priceDto);

    PersonDto.Base toDto(Person user);
    PersonDto.BaseForAdmin toPersonBaseForAdminDto(Person user);
    Person toPerson(AuthRequestDto request);
    Person fromDto(PersonDto.Create personDto);
    Person fromDto(PersonDto.Update personDto);
    Person fromDto(PersonDto.CreateUpdateForAdmin personDto);
    Person fromDto(PersonDto.Base personDto);
    Person fromDto(PersonDto.BaseForAdmin personDto);
    Person fromDto(PersonDto.Bind personDto);

    AddressDto toDto(Address address);
    Address fromDto(AddressDto addressDto);

    FavoriteDto toFavoriteDto(Favorite favorite);
    Favorite fromDto(FavoriteDto favoriteDto);

    DishDto.Base.Full toFullDto(Dish dish);
    DishDto.Base.Small toSmallDto(Dish dish);
    @Mapping(target = "portion", source = "dish.basePortion")
    DishDto.Base.ForOrder toDishForOrderDto(Dish dish);
    DishDto.Update toUpdateDishDto(Dish dish);
    DishDto.Bind toDto(Dish dish);
    DishDto.BindForOrder toBindForOrderDto(Dish dish);
    Dish fromDto(DishDto.Create dishDto);
    Dish fromDto(DishDto.Update dishDto);
    Dish fromDto(DishDto.Base.Full dishDto);
    Dish fromDto(DishDto.Base.Small dishDto);
    Dish fromDto(DishDto.Bind dishDto);
    @Mapping(target = "basePortion", source = "dishDto.portion")
    Dish fromDto(DishDto.BindForOrder dishDto);
    @Mapping(target = "category.name", source = "category")
    @Mapping(target = "basePortion", source = "dishDto.portion")
    Dish fromDto(DishDto.Base.ForOrder dishDto);


    ActionDto.Base.Small toSmallDto(Action action);
    ActionDto.Base.Full toFullDto(Action action);
    ActionDto.CreateUpdate toCreteUpdateActionDto(Action action);
    Action fromDto(ActionDto.CreateUpdate actionDto);
    Action fromDto(ActionDto.Base.Small actionDto);
    Action fromDto(ActionDto.Base.Full actionDto);

    OrderDto.Base.Small toSmallDto(Order order);
    OrderDto.Base.Full toFullDto(Order order);
    OrderDto.CreateUpdate toCreateUpdateOrderDto(Order order);
    Order fromDto(OrderDto.CreateUpdate orderDto);
    Order fromDto(OrderDto.Base.Small orderDto);
    Order fromDto(OrderDto.Base.Full orderDto);

    OrderStatusDto toDto(OrderStatus orderStatus);
    OrderStatus fromDto(OrderStatusDto status);

    @Mapping(target = "price", source = "addon.priceNow")
    AddonDto.Base toDto(Addon addon);
    @Mapping(target = "price", source = "addon.priceNow")
    AddonDto.CreateUpdate toCreateUpdateAddonDto(Addon addon);
    @Mapping(target = "priceNow", source = "addonDto.price")
    Addon fromDto(AddonDto.CreateUpdate addonDto);
    @Mapping(target = "priceNow", source = "addonDto.price")
    Addon fromDto(AddonDto.Base addonDto);
    Addon fromDto(AddonDto.Bind addonDto);

    WayToGetDto toDto(WayToGet wayToGet);
    WayToGet fromDto(WayToGetDto wayToGetDto);

    PaymentMethodDto toDto(PaymentMethod paymentMethod);
    PaymentMethod fromDto(PaymentMethodDto paymentMethodDto);

    DeliveryInformationDto toDto(DeliveryInformation deliveryInformation);
    DeliveryInformation fromDto(DeliveryInformationDto deliveryInformationDto);

    default Integer toDto(Favorite favorite) {
        return favorite.getDishId();
    }

    @Mapping(target = "dishId", source = "favorite")
    Favorite fromDto(Integer favorite);


    default List<ActionDto.Base.Small> toActionsDto(List<Action> actions) {
        if (actions == null) {
            return null;
        }

        return actions.stream()
                .map(this::toSmallDto)
                .collect(toList());
    }

    default List<CategoryDto> toCategoriesDto(List<Dish> dishes) {
        if (dishes == null) {
            return null;
        }

        return dishes.stream()
                .map(this::toSmallDto)
                .collect(Collectors.groupingBy(DishDto.Base.Small::getCategory))
                .entrySet().stream()
                .map(i -> new CategoryDto(i.getKey(), i.getValue()))
                .collect(Collectors.toList());
    }

    default List<Dish> fromCategoriesDto(List<CategoryDto> categoryDtos) {
        if (categoryDtos == null) {
            return null;
        }

        return categoryDtos.stream()
                .flatMap(c -> c.getDishes().stream())
                .map(this::fromDto)
                .collect(toList());
    }


    default List<DishDto.Base.Small> toSmallDishesDto(List<Dish> dishes) {
        if (dishes == null) {
            return null;
        }

        return dishes.stream()
                .map(this::toSmallDto)
                .collect(Collectors.toList());
    }

    default List<DishDto.Base.Full> toFullDishesDto(List<Dish> dishes) {
        if (dishes == null) {
            return null;
        }

        return dishes.stream()
                .map(this::toFullDto)
                .collect(Collectors.toList());
    }


    default List<OrderDto.Base.Small> toOrdersDto(List<Order> orders) {
        if (orders == null) {
            return null;
        }

        return orders.stream()
                .map(this::toSmallDto)
                .collect(Collectors.toList());

    }

    default List<AddonDto.Base> toDto(List<Addon> addons) {
        if (addons == null) {
            return null;
        }

        return addons.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    default LocalDateTime toLocalDateTime(Date date) {
        if (date == null) {
            return null;
        }

        return date.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime();
    }

    default Date toDate(LocalDateTime localDateTime) {
        return java.sql.Timestamp.valueOf(localDateTime);
    }

    default List<Integer> toFavoritesDto(List<Favorite> favorites) {
        if (favorites == null) {
            return null;
        }

        return favorites.stream()
                .map(Favorite::getDishId)
                .collect(toList());
    }

    default AddonDto.BindForOrder toBindForOrderDto(OrderedAddon orderedAddon) {
        if (orderedAddon == null) {
            return null;
        }

        return AddonDto.BindForOrder.builder()
                .id(orderedAddon.getId())
                .price(toBindDto(orderedAddon.getPrice()))
                .build();
    }

    default AddonDto.Base toDto(OrderedAddon orderedAddon) {
        if (orderedAddon == null) {
            return null;
        }

        return AddonDto.Base.builder()
                .id(orderedAddon.getId())
                .price(toDto(orderedAddon.getPrice()))
                .deleted(orderedAddon.getAddon().getDeleted())
                .name(orderedAddon.getAddon().getName())
                .picturePath(orderedAddon.getAddon().getPicturePath())
                .build();
    }

    default List<AddonDto.Base> toDtoFromAddonsPrices(List<OrderedAddon> addonsPrices) {
        if (addonsPrices == null) {
            return null;
        }
        return addonsPrices.stream()
                .map(this::toDto)
                .collect(toList());
    }

    default List<AddonDto.BindForOrder> toBindForOrderDtoFromAddonsPrices(List<OrderedAddon> addonsPrices) {
        if (addonsPrices == null) {
            return null;
        }
        return addonsPrices.stream()
                .map(this::toBindForOrderDto)
                .collect(toList());
    }


    Addon fromBindForOrderDto(AddonDto.BindForOrder addonDto);

    default OrderedAddon fromDto(AddonDto.BindForOrder addonsDtos) {
        if (addonsDtos == null) {
            return null;
        }

        return OrderedAddon.builder()
                .price(fromDto(addonsDtos.getPrice()))
                .addon(fromBindForOrderDto(addonsDtos))
                .build();
    }


    default List<OrderedAddon> fromDto(List<AddonDto.BindForOrder> addonsDtos) {
        if (addonsDtos == null) {
            return null;
        }

        return addonsDtos.stream()
                .map(this::fromDto)
                .collect(toList());
    }


    default DishCountDto.Base toDto(OrderedDish dishesCount) {
        if (dishesCount == null) {
            return null;
        }
        var dish = toDishForOrderDto(dishesCount.getDish());

        dish.setPortion(toDto(dishesCount.getPortion()));
        dish.getPortion().setPriceNow(toDto(dishesCount.getDishPrice()));
        dish.setAddons(toDtoFromAddonsPrices(dishesCount.getOrderedAddons()));

        return DishCountDto.Base.builder()
                .count(dishesCount.getCount())
                .dish(dish)
                .build();
    }


    default DishCountDto.CreteUpdate toCreateUpdateDto(OrderedDish dishesCount) {
        if (dishesCount == null) {
            return null;
        }
        var dish = toBindForOrderDto(dishesCount.getDish());

        dish.setPortion(toBindDto(dishesCount.getPortion()));
        dish.getPortion().setPrice(toBindDto(dishesCount.getDishPrice()));
        dish.setAddons(toBindForOrderDtoFromAddonsPrices(dishesCount.getOrderedAddons()));

        return DishCountDto.CreteUpdate.builder()
                .count(dishesCount.getCount())
                .dish(dish)
                .build();
    }


    default OrderedDish fromDto(DishCountDto.CreteUpdate dishCountDto) {
        if (dishCountDto == null) {
            return null;
        }

        return OrderedDish.builder()
                .count(dishCountDto.getCount())
                .dish(fromDto(dishCountDto.getDish()))
                .dishPrice(fromDto(dishCountDto.getDish().getPortion().getPrice()))
                .portion(fromDto(dishCountDto.getDish().getPortion()))
                .orderedAddons(fromDto(dishCountDto.getDish().getAddons()))
                .build();
    }

    default Category fromDto(String categoryName) {
        if (categoryName == null) {
            return null;
        }

        Category.CategoryBuilder category = Category.builder();
        category.name(categoryName);
        return category.build();
    }

    default String toStringCategoryDto(Category category) {
        if (category == null) {
            return null;
        }
        return category.getName();
    }
}