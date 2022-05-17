package ru.bironix.super_food.config.formatters;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.format.Formatter;
import org.springframework.stereotype.Service;
import ru.bironix.super_food.dtos.dish.DishDto;
import ru.bironix.super_food.utils.Utils;

import java.util.Locale;

@Service
public class BindDishDtoFormatter implements Formatter<DishDto.Bind> {


    @Override
    public String print(DishDto.Bind object, Locale locale) {
        try {
            return Utils.customMapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public DishDto.Bind parse(String text, Locale locale) {
                
        try {
            return Utils.customMapper.readValue(text, DishDto.Bind.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }
}