package ru.bironix.super_food.config.formatters;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.format.Formatter;
import org.springframework.stereotype.Service;
import ru.bironix.super_food.dtos.order.OrderStatusDto;
import ru.bironix.super_food.dtos.person.AddressDto;
import ru.bironix.super_food.utils.Utils;

import java.util.Arrays;
import java.util.Locale;

@Service
public class OrderStatusDtoFormatter implements Formatter<OrderStatusDto> {

    @Override
    public String print(OrderStatusDto object, Locale locale) {
        return object.getRusName();
    }

    @Override
    public OrderStatusDto parse(String text, Locale locale) {
        return Arrays.stream(OrderStatusDto.values())
                .filter(o -> o.getRusName().equals(text))
                .findAny()
                .orElse(null);
    }
}