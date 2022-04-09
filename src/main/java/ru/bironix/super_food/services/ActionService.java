package ru.bironix.super_food.services;

import org.springframework.stereotype.Service;
import ru.bironix.super_food.Utils;
import ru.bironix.super_food.dtos.action.FullActionDto;
import ru.bironix.super_food.dtos.action.SmallActionDto;
import ru.bironix.super_food.dtos.dish.CategoryType;
import ru.bironix.super_food.dtos.responses.ActionsResponseDto;

import java.util.List;

@Service
public class ActionService {

    //TODO
    public ActionsResponseDto getActions() {
        return ActionsResponseDto.builder()
                .actions(
                        List.of(
                                SmallActionDto.builder()
                                        .id(0)
                                        .name("Бутеры")
                                        .picturePaths(Utils.getMockPicturesDto())
                                        .build(),

                                SmallActionDto.builder()
                                        .id(1)
                                        .name("Бутеры")
                                        .picturePaths(Utils.getMockPicturesDto())
                                        .build(),

                                SmallActionDto.builder()
                                        .id(2)
                                        .name("Бутеры")
                                        .picturePaths(Utils.getMockPicturesDto())
                                        .build()

                        ))
                .build();
    }

    //TODO
    public FullActionDto getAction(int id) {
        return FullActionDto.builder()
                .id(id)
                .name("Две утки по цене трёх")
                .picturePaths(Utils.getMockPicturesDto())
                .dishes(List.of(
                        Utils.getMockSmallDishDto(0, CategoryType.ROLLS),
                        Utils.getMockSmallDishDto(1, CategoryType.BURGERS),
                        Utils.getMockSmallDishComboDto(1)

                ))
                .build();
    }
}