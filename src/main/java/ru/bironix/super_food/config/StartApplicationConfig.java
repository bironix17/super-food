package ru.bironix.super_food.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import ru.bironix.super_food.services.DishService;
import ru.bironix.super_food.services.PersonService;
import ru.bironix.super_food.store.db.models.person.Person;
import ru.bironix.super_food.store.db.models.person.Role;

import static ru.bironix.super_food.constants.Constants.*;

@Component
public class StartApplicationConfig {

    private final PersonService personService;
    private final DishService dishService;

    @Autowired
    public StartApplicationConfig(PersonService personService, DishService dishService) {
        this.personService = personService;
        this.dishService = dishService;
    }

    @EventListener(ApplicationReadyEvent.class)
    public void doSomethingAfterStartup() {

        if (!personService.getPersonByPhoneNumber(ADMIN_PHONE_NUMBER).isPresent()) {
            personService.createPerson(Person.builder()
                    .phoneNumber(ADMIN_PHONE_NUMBER)
                    .password("admin")
                    .name("admin")
                    .role(Role.ROLE_ADMIN)
                    .build());
        }


        if (!personService.getPersonByPhoneNumber(DELETED_PERSON_PHONE_NUMBER).isPresent()) {
            var person = personService.createPerson(Person.builder()
                    .phoneNumber(DELETED_PERSON_PHONE_NUMBER)
                    .password("deletedUser")
                    .name("deletedUser")
                    .role(Role.ROLE_CLIENT)
                    .build());
            personService.addAddressForPerson(person.getId(), "deleted");
        }

        try {
            dishService.getCategory(COMBO);
        } catch (Exception e) {
            dishService.createCategory(COMBO);
        }

        checkExistsDishCategory(COMBO);
        checkExistsDishCategory(BURGERS);
        checkExistsDishCategory(PIZZA);
        checkExistsDishCategory(SALADS);
    }

    void checkExistsDishCategory(String name) {
        try {
            dishService.getCategory(name);
        } catch (Exception e) {
            dishService.createCategory(name);
        }
    }
}
