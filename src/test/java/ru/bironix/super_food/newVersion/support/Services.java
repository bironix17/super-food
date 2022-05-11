package ru.bironix.super_food.newVersion.support;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.bironix.super_food.services.*;

@Component
public class Services {
    public final ActionService actionService;
    public final DishService dishService;
    public final InformationService informationService;
    public final OrderService orderService;
    public final PersonService personService;

    @Autowired
    public Services(ActionService actionService,
                    DishService dishService,
                    InformationService informationService,
                    OrderService orderService,
                    PersonService personService) {
        this.actionService = actionService;
        this.dishService = dishService;
        this.informationService = informationService;
        this.orderService = orderService;
        this.personService = personService;
    }
}
