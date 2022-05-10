package ru.bironix.super_food.dtos.interfaces.addon;

import ru.bironix.super_food.dtos.dish.AddonDto;

import javax.validation.Valid;
import java.util.List;

public interface Addons {
    @Valid
    List<AddonDto.Base> getAddons();
}
