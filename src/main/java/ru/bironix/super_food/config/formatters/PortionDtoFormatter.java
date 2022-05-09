package ru.bironix.super_food.config.formatters;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.format.Formatter;
import org.springframework.stereotype.Service;
import ru.bironix.super_food.dtos.dish.PortionDto;
import ru.bironix.super_food.utils.Utils;

import java.util.Locale;

@Service
public class PortionDtoFormatter implements Formatter<PortionDto.Base> {


    @Override
    public String print(PortionDto.Base object, Locale locale) {
        try {
            return Utils.customMapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return null;
    }


    // TODO разобраться почему приходит отрывок "{"id":null"
    @Override
    public PortionDto.Base parse(String text, Locale locale) {
        try {
            return Utils.customMapper.readValue(text, PortionDto.Base.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }
}