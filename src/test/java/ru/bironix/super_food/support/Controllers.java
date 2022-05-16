package ru.bironix.super_food.support;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.bironix.super_food.controllers.*;
import ru.bironix.super_food.controllers.person.MyPersonController;
import ru.bironix.super_food.controllers.person.PersonController;

@Component
public class Controllers {
    final public MyPersonController myPersonController;
    final public PersonController personController;
    final public ActionController actionController;
    final public AuthController authController;
    final public DishController dishController;
    final public InformationController informationController;
    final public OrderController orderController;

    @Autowired
    public Controllers(MyPersonController myPersonController,
                       PersonController personController,
                       ActionController actionController,
                       AuthController authController,
                       DishController dishController,
                       InformationController informationController,
                       OrderController orderController) {
        this.myPersonController = myPersonController;
        this.personController = personController;
        this.actionController = actionController;
        this.authController = authController;
        this.dishController = dishController;
        this.informationController = informationController;
        this.orderController = orderController;
    }
}
