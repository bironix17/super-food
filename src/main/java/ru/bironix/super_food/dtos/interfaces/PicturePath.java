package ru.bironix.super_food.dtos.interfaces;

import javax.validation.constraints.NotNull;

public interface PicturePath {

    @NotNull
    String getPicturePath();
}
