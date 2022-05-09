package ru.bironix.super_food.config.formatters;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.format.Formatter;
import org.springframework.stereotype.Service;
import ru.bironix.super_food.dtos.dish.DishCountDto;
import ru.bironix.super_food.utils.Utils;

import java.util.Locale;

@Service
public class DishCountDtoFormatter implements Formatter<DishCountDto> {


    @Override
    public String print(DishCountDto object, Locale locale) {
        try {
            return Utils.customMapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public DishCountDto parse(String text, Locale locale) {
        try {
            return Utils.customMapper.readValue(text, DishCountDto.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }
}