package ru.bironix.super_food.config.formatters;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.format.Formatter;
import org.springframework.stereotype.Service;
import ru.bironix.super_food.dtos.dish.FullDishDto;
import ru.bironix.super_food.dtos.dish.PortionDto;

import java.util.Locale;

@Service
public class PortionDtoFormatter implements Formatter<PortionDto> {


    @Override
    public String print(PortionDto object, Locale locale) {
        ObjectMapper mapper = new ObjectMapper();

        try {
            return mapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return null;
    }


    // TODO разобраться почему приходит отрывок "{"id":null"
    @Override
    public PortionDto parse(String text, Locale locale) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.readValue(text, PortionDto.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }
}