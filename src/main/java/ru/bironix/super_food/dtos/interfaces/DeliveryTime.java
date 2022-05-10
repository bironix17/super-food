package ru.bironix.super_food.dtos.interfaces;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

public interface DeliveryTime {

    @JsonFormat(pattern = "HH:mm")
    Date getDeliveryTime();
}
