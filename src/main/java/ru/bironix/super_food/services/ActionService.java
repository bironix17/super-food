package ru.bironix.super_food.services;

import org.springframework.stereotype.Service;
import ru.bironix.super_food.Utils;
import ru.bironix.super_food.models.action.ActionType;
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
                        .actionType(ActionType.COMBO_DISCOUNT)
                        .build(),

                SmallActionDto.builder()
                        .id(1)
                        .name("Бутеры")
                        .picturePaths(Utils.getMockPicturesDto())
                        .actionType(ActionType.COMBO_GIFT)
                        .build(),

                SmallActionDto.builder()
                        .id(2)
                        .name("Бутеры")
                        .picturePaths(Utils.getMockPicturesDto())
                        .actionType(ActionType.DISH_DISCOUNT)
                        .build()

        );
    }

    //TODO
    public FullActionDto getAction(int id) {

        if (id == 0) {
            return FullActionDto.builder()
                    .id(id)
                    .name("Две утки по цене трёх")
                    .actionType(ActionType.COMBO_DISCOUNT)
                    .picturePaths(Utils.getMockPicturesDto())
                    .build();
        } else if (id == 1) {
            return FullActionDto.builder()
                    .id(id)
                    .name("Две утки по цене трёх")
                    .actionType(ActionType.COMBO_GIFT)
                    .picturePaths(Utils.getMockPicturesDto())
                    .build();
        } else if (id == 2) {
            FullActionDto.builder()
                    .id(id)
                    .name("Две утки по цене трёх")
                    .actionType(ActionType.DISH_DISCOUNT)
                    .picturePaths(Utils.getMockPicturesDto())
                    .build();
        }
        return null;
    }
}
