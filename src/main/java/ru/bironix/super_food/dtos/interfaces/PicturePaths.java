package ru.bironix.super_food.dtos.interfaces;

import ru.bironix.super_food.dtos.common.PicturePathsDto;

import javax.validation.constraints.NotNull;

public interface PicturePaths {

    @NotNull
    PicturePathsDto getPicturePaths();
}
