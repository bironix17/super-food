package ru.bironix.super_food.db.models.order;

import lombok.*;
import ru.bironix.super_food.db.models.dish.Dish;
import ru.bironix.super_food.db.models.person.Address;
import ru.bironix.super_food.db.models.person.Person;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "food_order")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    Integer id;

    LocalDateTime created;
    LocalDateTime targetProduction;

    int totalPrice;

    @Enumerated(EnumType.STRING)
    Status status;

    @Enumerated(EnumType.STRING)
    WayToGet wayToGet;

    //TODO изучить
    @ManyToMany
    @JoinColumns({
            @JoinColumn(name = "order_id"),
            @JoinColumn(name = "dish_id")
    })
    List<Dish> dishes;

    @OneToOne
    Person client;

    @OneToOne
    Address address;
}
