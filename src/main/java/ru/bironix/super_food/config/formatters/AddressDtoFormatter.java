package ru.bironix.super_food.config.formatters;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.format.Formatter;
import org.springframework.stereotype.Service;
import ru.bironix.super_food.dtos.person.AddressDto;

import java.util.Locale;

@Service
public class AddressDtoFormatter implements Formatter<AddressDto> {

    @Override
    public String print(AddressDto object, Locale locale) {
        ObjectMapper mapper = new ObjectMapper();

        try {
            return mapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public AddressDto parse(String text, Locale locale) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.readValue(text, AddressDto.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }
}