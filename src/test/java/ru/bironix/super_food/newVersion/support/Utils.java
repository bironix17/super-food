package ru.bironix.super_food.newVersion.support;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.bironix.super_food.converters.Converter;
import ru.bironix.super_food.dtos.AuthRequestDto;
import ru.bironix.super_food.dtos.dish.AddonDto;
import ru.bironix.super_food.dtos.person.PersonDto;
import ru.bironix.super_food.store.db.models.person.Person;

@Component
public class Utils {
    public final Converter con;

    @Autowired
    public Utils(Converter con) {
        this.con = con;
    }


    public AuthRequestDto getAuthRequest(Person person) {
        return AuthRequestDto.builder()
                .email(person.getEmail())
                .password(person.getPassword())
                .build();
    }

    public PersonDto.Update personToUpdateDto(Person person) {
        return PersonDto.Update.builder()
                .name(person.getName())
                .phoneNumber(person.getPhoneNumber())
                .build();
    }


}