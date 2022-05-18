package ru.bironix.super_food.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import ru.bironix.super_food.exceptions.NotFoundSourceException;
import ru.bironix.super_food.services.PersonService;
import ru.bironix.super_food.store.db.models.person.Address;
import ru.bironix.super_food.store.db.models.person.Person;
import ru.bironix.super_food.store.db.models.person.Role;

import java.util.List;

import static ru.bironix.super_food.constants.Constants.ADMIN_EMAIL;
import static ru.bironix.super_food.constants.Constants.DELETED_PERSON_EMAIL;

@Component
public class StartApplicationConfig {

    private final PersonService personService;

    @Autowired
    public StartApplicationConfig(PersonService personService) {
        this.personService = personService;
    }


    @EventListener(ApplicationReadyEvent.class)
    public void doSomethingAfterStartup() {

        if (!personService.getPersonByEmail(ADMIN_EMAIL).isPresent()) {
            personService.createPerson(Person.builder()
                    .email(ADMIN_EMAIL)
                    .password("admin")
                    .name("admin")
                    .role(Role.ROLE_ADMIN)
                    .build());
        }


        if (!personService.getPersonByEmail(DELETED_PERSON_EMAIL).isPresent()) {
           var person =  personService.createPerson(Person.builder()
                    .email(DELETED_PERSON_EMAIL)
                    .password("deletedUser")
                    .name("deletedUser")
                    .role(Role.ROLE_CLIENT)
                    .build());
            personService.addAddressForPerson(person.getId(), "deleted");
        }

    }
}
