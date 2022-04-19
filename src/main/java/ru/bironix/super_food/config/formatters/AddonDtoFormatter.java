package ru.bironix.super_food.config.formatters;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.format.Formatter;
import org.springframework.stereotype.Service;
import ru.bironix.super_food.dtos.dish.AddonDto;

import java.util.Locale;

@Service
public class AddonDtoFormatter implements Formatter<AddonDto> {

    @Override
    public String print(AddonDto object, Locale locale) {
        ObjectMapper mapper = new ObjectMapper();

        try {
            return mapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public AddonDto parse(String text, Locale locale) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.readValue(text, AddonDto.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }
}