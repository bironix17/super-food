package ru.bironix.super_food.config;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.format.Formatter;
import org.springframework.stereotype.Service;
import ru.bironix.super_food.dtos.dish.FullDishDto;

import java.util.Locale;

@Service
public class FullDishDtoFormatter implements Formatter<FullDishDto> {


    @Override
    public String print(FullDishDto object, Locale locale) {
        ObjectMapper mapper = new ObjectMapper();

        try {
            return mapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public FullDishDto parse(String text, Locale locale) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.readValue(text, FullDishDto.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }
}