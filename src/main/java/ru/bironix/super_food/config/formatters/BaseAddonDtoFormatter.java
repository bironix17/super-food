package ru.bironix.super_food.config.formatters;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.format.Formatter;
import org.springframework.stereotype.Service;
import ru.bironix.super_food.dtos.dish.AddonDto;
import ru.bironix.super_food.utils.Utils;

import java.util.Locale;

@Service
public class BaseAddonDtoFormatter implements Formatter<AddonDto.Base> {

    @Override
    public String print(AddonDto.Base object, Locale locale) {
        try {
            return Utils.customMapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public AddonDto.Base parse(String text, Locale locale) {
        try {
            return Utils.customMapper.readValue(text, AddonDto.Base.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }
}