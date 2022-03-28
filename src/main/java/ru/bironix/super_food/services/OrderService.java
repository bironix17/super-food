package ru.bironix.super_food.services;

import org.springframework.stereotype.Service;
import ru.bironix.super_food.Utils;
import ru.bironix.super_food.models.order.OrderDto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class
OrderService {

    public OrderDto getOrder(int id) {
        return OrderDto.builder()
                .id(id)
                .dateTime(new Date())
                .totalPrice(100)
                .dishes(List.of(Utils.getMockSmallDishDto(1)))
                .user(Utils.getMockUser())
                .build();
    }
}
