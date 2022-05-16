package ru.bironix.super_food.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import ru.bironix.super_food.exceptions.NotFoundSourceException;
import ru.bironix.super_food.services.PersonService;
import ru.bironix.super_food.store.db.models.person.Person;
import ru.bironix.super_food.store.db.models.person.Role;

@Component
public class StartApplicationConfig {

    private final PersonService personService;

    @Autowired
    public StartApplicationConfig(PersonService personService) {
        this.personService = personService;
    }

    @EventListener(ApplicationReadyEvent.class)
    public void doSomethingAfterStartup() {
        try {
            personService.getPerson(1);
        } catch (NotFoundSourceException e) {
            personService.createPerson(Person.builder()
                    .email("admin@admin.ru")
                    .password("admin")
                    .name("admin")
                    .role(Role.ROLE_ADMIN)
                    .build());
        }
    }
}
