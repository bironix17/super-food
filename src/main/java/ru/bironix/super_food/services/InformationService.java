package ru.bironix.super_food.services;

import org.springframework.stereotype.Service;
import ru.bironix.super_food.constants.ApiError;
import ru.bironix.super_food.db.models.dish.Dish;
import ru.bironix.super_food.db.models.person.Address;
import ru.bironix.super_food.db.models.person.Favorite;
import ru.bironix.super_food.db.models.person.Person;
import ru.bironix.super_food.exceptions.ApiException;
import ru.bironix.super_food.exceptions.NotFoundSourceException;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toSet;

@Service
public class InformationService {

    public Integer getDeliveryPrice(){
        return 200;
    }
}