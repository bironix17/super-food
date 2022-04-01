package ru.bironix.super_food.services;

import org.springframework.stereotype.Service;
import ru.bironix.super_food.Utils;
import ru.bironix.super_food.models.combo.ComboDishesDto;
import ru.bironix.super_food.models.combo.ComboTypeDto;
import ru.bironix.super_food.models.action.ActionTypeDto;
import ru.bironix.super_food.models.action.FullActionDto;
import ru.bironix.super_food.models.action.SmallActionDto;

import java.util.List;

@Service
public class ActionService {

    //TODO
    public List<SmallActionDto> getActions() {
        return List.of(
                SmallActionDto.builder()
                        .id(0)
                        .name("Бутеры")
                        .picturePaths(Utils.getMockPicturesDto())
                        .actionType(ActionTypeDto.COMBO_DISCOUNT)
                        .build(),

                SmallActionDto.builder()
                        .id(1)
                        .name("Бутеры")
                        .picturePaths(Utils.getMockPicturesDto())
                        .actionType(ActionTypeDto.COMBO_GIFT)
                        .build(),

                SmallActionDto.builder()
                        .id(2)
                        .name("Бутеры")
                        .picturePaths(Utils.getMockPicturesDto())
                        .actionType(ActionTypeDto.DISH_DISCOUNT)
                        .build()

        );
    }

    //TODO
    public FullActionDto getAction(int id) {

        if (id == 0) {
            return FullActionDto.builder()
                    .id(id)
                    .name("Две утки по цене трёх")
                    .actionType(ActionTypeDto.COMBO_DISCOUNT)
                    .picturePaths(Utils.getMockPicturesDto())
                    .combos(List.of(
                            ComboDishesDto.builder()
                                    .id(0)
                                    .name("Утка кря")
                                    .totalPrice(100)
                                    .comboType(ComboTypeDto.COMBO_DISCOUNT)
                                    .dishes(List.of(Utils.getMockSmallDishDto(0),
                                            Utils.getMockSmallDishDto(1)))
                                    .build()

                    ))
                    .build();
        } else if (id == 1) {
            return FullActionDto.builder()
                    .id(id)
                    .name("Две утки по цене трёх")
                    .actionType(ActionTypeDto.COMBO_GIFT)
                    .picturePaths(Utils.getMockPicturesDto())
                    .combos(List.of(
                            ComboDishesDto.builder()
                                    .id(0)
                                    .name("Утка кря")
                                    .comboType(ComboTypeDto.COMBO_GIFT)
                                    .dishes(List.of(Utils.getMockSmallDishDto(0),
                                            Utils.getMockSmallDishDto(1)))
                                    .giftDish(Utils.getMockSmallDishDto(3))
                                    .build()

                    ))
                    .build();
        } else if (id == 2) {
            FullActionDto.builder()
                    .id(id)
                    .name("Две утки по цене трёх")
                    .actionType(ActionTypeDto.DISH_DISCOUNT)
                    .picturePaths(Utils.getMockPicturesDto())
                    .discountDishes(List.of(
                            Utils.getMockSmallDishDto(0),
                            Utils.getMockSmallDishDto(1)
                    ))
                    .build();
        }
        return null;
    }
}
