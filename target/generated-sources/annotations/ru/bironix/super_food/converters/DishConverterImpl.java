package ru.bironix.super_food.converters;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;
import ru.bironix.super_food.db.dish.models.Addon;
import ru.bironix.super_food.db.dish.models.Addon.AddonBuilder;
import ru.bironix.super_food.db.dish.models.CategoryType;
import ru.bironix.super_food.db.dish.models.Dish;
import ru.bironix.super_food.db.dish.models.Dish.DishBuilder;
import ru.bironix.super_food.db.dish.models.PicturePaths;
import ru.bironix.super_food.db.dish.models.PicturePaths.PicturePathsBuilder;
import ru.bironix.super_food.db.dish.models.Portion;
import ru.bironix.super_food.db.dish.models.Portion.PortionBuilder;
import ru.bironix.super_food.db.dish.models.Price;
import ru.bironix.super_food.db.dish.models.Price.PriceBuilder;
import ru.bironix.super_food.dtos.PicturePathsDto;
import ru.bironix.super_food.dtos.dish.AddonDto;
import ru.bironix.super_food.dtos.dish.FullDishDto;
import ru.bironix.super_food.dtos.dish.PortionDto;
import ru.bironix.super_food.dtos.dish.PortionDto.PortionDtoBuilder;
import ru.bironix.super_food.dtos.dish.PriceDto;
import ru.bironix.super_food.dtos.dish.PriceDto.PriceDtoBuilder;
import ru.bironix.super_food.dtos.dish.SmallDishDto;
import ru.bironix.super_food.dtos.dish.SmallDishDto.SmallDishDtoBuilder;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-04-15T11:50:04+0300",
    comments = "version: 1.4.2.Final, compiler: javac, environment: Java 11.0.14.1 (BellSoft)"
)
@Component
public class DishConverterImpl implements DishConverter {

    @Override
    public SmallDishDto toSmallDishDto(Dish dish) {
        if ( dish == null ) {
            return null;
        }

        SmallDishDtoBuilder<?, ?> smallDishDto = SmallDishDto.builder();

        smallDishDto.id( dish.getId() );
        smallDishDto.picturePaths( toDto( dish.getPicturePaths() ) );
        smallDishDto.name( dish.getName() );
        smallDishDto.composition( dish.getComposition() );
        smallDishDto.category( categoryTypeToCategoryType( dish.getCategory() ) );

        return smallDishDto.build();
    }

    @Override
    public SmallDishDto toFullDishDto(Dish dish) {
        if ( dish == null ) {
            return null;
        }

        SmallDishDtoBuilder<?, ?> smallDishDto = SmallDishDto.builder();

        smallDishDto.id( dish.getId() );
        smallDishDto.picturePaths( toDto( dish.getPicturePaths() ) );
        smallDishDto.name( dish.getName() );
        smallDishDto.composition( dish.getComposition() );
        smallDishDto.category( categoryTypeToCategoryType( dish.getCategory() ) );

        return smallDishDto.build();
    }

    @Override
    public Dish fromFullDishDto(FullDishDto dishDto) {
        if ( dishDto == null ) {
            return null;
        }

        DishBuilder<?, ?> dish = Dish.builder();

        dish.id( dishDto.getId() );
        dish.picturePaths( fromDto( dishDto.getPicturePaths() ) );
        dish.name( dishDto.getName() );
        dish.composition( dishDto.getComposition() );
        dish.category( categoryTypeToCategoryType1( dishDto.getCategory() ) );
        dish.description( dishDto.getDescription() );
        dish.allergens( dishDto.getAllergens() );
        dish.baseIndexPortion( dishDto.getBaseIndexPortion() );
        dish.portions( portionDtoListToPortionList( dishDto.getPortions() ) );
        dish.addons( addonDtoListToAddonList( dishDto.getAddons() ) );
        dish.dishes( smallDishDtoListToDishList( dishDto.getDishes() ) );

        return dish.build();
    }

    @Override
    public AddonDto toDto(Addon addon) {
        if ( addon == null ) {
            return null;
        }

        Integer id = null;
        String name = null;
        String picturePath = null;
        PriceDto priceNow = null;

        id = addon.getId();
        name = addon.getName();
        picturePath = addon.getPicturePath();
        priceNow = toDto( addon.getPriceNow() );

        AddonDto addonDto = new AddonDto( id, name, picturePath, priceNow );

        return addonDto;
    }

    @Override
    public Addon fromDto(AddonDto addonDto) {
        if ( addonDto == null ) {
            return null;
        }

        AddonBuilder<?, ?> addon = Addon.builder();

        addon.id( addonDto.getId() );
        addon.name( addonDto.getName() );
        addon.picturePath( addonDto.getPicturePath() );
        addon.priceNow( fromDto( addonDto.getPriceNow() ) );

        return addon.build();
    }

    @Override
    public PicturePathsDto toDto(PicturePaths picturePaths) {
        if ( picturePaths == null ) {
            return null;
        }

        String large = null;

        large = picturePaths.getLarge();

        PicturePathsDto picturePathsDto = new PicturePathsDto( large );

        picturePathsDto.setSmall( picturePaths.getSmall() );
        picturePathsDto.setMedium( picturePaths.getMedium() );

        return picturePathsDto;
    }

    @Override
    public PicturePaths fromDto(PicturePathsDto picturePathsDto) {
        if ( picturePathsDto == null ) {
            return null;
        }

        PicturePathsBuilder<?, ?> picturePaths = PicturePaths.builder();

        picturePaths.small( picturePathsDto.getSmall() );
        picturePaths.medium( picturePathsDto.getMedium() );
        picturePaths.large( picturePathsDto.getLarge() );

        return picturePaths.build();
    }

    @Override
    public PortionDto toDto(Portion portion) {
        if ( portion == null ) {
            return null;
        }

        PortionDtoBuilder portionDto = PortionDto.builder();

        portionDto.id( portion.getId() );
        portionDto.size( portion.getSize() );
        portionDto.priceNow( toDto( portion.getPriceNow() ) );
        portionDto.oldPrice( toDto( portion.getOldPrice() ) );

        return portionDto.build();
    }

    @Override
    public Portion fromDto(PortionDto portionDto) {
        if ( portionDto == null ) {
            return null;
        }

        PortionBuilder<?, ?> portion = Portion.builder();

        portion.id( portionDto.getId() );
        portion.size( portionDto.getSize() );
        portion.priceNow( fromDto( portionDto.getPriceNow() ) );
        portion.oldPrice( fromDto( portionDto.getOldPrice() ) );

        return portion.build();
    }

    @Override
    public PriceDto toDto(Price price) {
        if ( price == null ) {
            return null;
        }

        PriceDtoBuilder priceDto = PriceDto.builder();

        priceDto.id( price.getId() );
        priceDto.price( price.getPrice() );

        return priceDto.build();
    }

    @Override
    public Price fromDto(PriceDto priceDto) {
        if ( priceDto == null ) {
            return null;
        }

        PriceBuilder<?, ?> price = Price.builder();

        price.id( priceDto.getId() );
        price.price( priceDto.getPrice() );

        return price.build();
    }

    protected ru.bironix.super_food.dtos.dish.CategoryType categoryTypeToCategoryType(CategoryType categoryType) {
        if ( categoryType == null ) {
            return null;
        }

        ru.bironix.super_food.dtos.dish.CategoryType categoryType1;

        switch ( categoryType ) {
            case BURGERS: categoryType1 = ru.bironix.super_food.dtos.dish.CategoryType.BURGERS;
            break;
            case ROLLS: categoryType1 = ru.bironix.super_food.dtos.dish.CategoryType.ROLLS;
            break;
            case PIZZA: categoryType1 = ru.bironix.super_food.dtos.dish.CategoryType.PIZZA;
            break;
            case DRINKS: categoryType1 = ru.bironix.super_food.dtos.dish.CategoryType.DRINKS;
            break;
            case SAUCES: categoryType1 = ru.bironix.super_food.dtos.dish.CategoryType.SAUCES;
            break;
            case COMBO: categoryType1 = ru.bironix.super_food.dtos.dish.CategoryType.COMBO;
            break;
            default: throw new IllegalArgumentException( "Unexpected enum constant: " + categoryType );
        }

        return categoryType1;
    }

    protected CategoryType categoryTypeToCategoryType1(ru.bironix.super_food.dtos.dish.CategoryType categoryType) {
        if ( categoryType == null ) {
            return null;
        }

        CategoryType categoryType1;

        switch ( categoryType ) {
            case BURGERS: categoryType1 = CategoryType.BURGERS;
            break;
            case ROLLS: categoryType1 = CategoryType.ROLLS;
            break;
            case PIZZA: categoryType1 = CategoryType.PIZZA;
            break;
            case DRINKS: categoryType1 = CategoryType.DRINKS;
            break;
            case SAUCES: categoryType1 = CategoryType.SAUCES;
            break;
            case COMBO: categoryType1 = CategoryType.COMBO;
            break;
            default: throw new IllegalArgumentException( "Unexpected enum constant: " + categoryType );
        }

        return categoryType1;
    }

    protected List<Portion> portionDtoListToPortionList(List<PortionDto> list) {
        if ( list == null ) {
            return null;
        }

        List<Portion> list1 = new ArrayList<Portion>( list.size() );
        for ( PortionDto portionDto : list ) {
            list1.add( fromDto( portionDto ) );
        }

        return list1;
    }

    protected List<Addon> addonDtoListToAddonList(List<AddonDto> list) {
        if ( list == null ) {
            return null;
        }

        List<Addon> list1 = new ArrayList<Addon>( list.size() );
        for ( AddonDto addonDto : list ) {
            list1.add( fromDto( addonDto ) );
        }

        return list1;
    }

    protected Dish smallDishDtoToDish(SmallDishDto smallDishDto) {
        if ( smallDishDto == null ) {
            return null;
        }

        DishBuilder<?, ?> dish = Dish.builder();

        dish.id( smallDishDto.getId() );
        dish.picturePaths( fromDto( smallDishDto.getPicturePaths() ) );
        dish.name( smallDishDto.getName() );
        dish.composition( smallDishDto.getComposition() );
        dish.category( categoryTypeToCategoryType1( smallDishDto.getCategory() ) );

        return dish.build();
    }

    protected List<Dish> smallDishDtoListToDishList(List<SmallDishDto> list) {
        if ( list == null ) {
            return null;
        }

        List<Dish> list1 = new ArrayList<Dish>( list.size() );
        for ( SmallDishDto smallDishDto : list ) {
            list1.add( smallDishDtoToDish( smallDishDto ) );
        }

        return list1;
    }
}
