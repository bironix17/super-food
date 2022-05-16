package ru.bironix.super_food.dtos.interfaces;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.validation.constraints.NotNull;
import java.util.Date;

public interface DeliveryTime {

    @JsonFormat(pattern = "HH:mm")
    @NotNull
    Date getDeliveryTime();
}
