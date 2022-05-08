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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    LocalDateTime created;
    LocalDateTime deliveryTime;

    int totalPrice;

    @Enumerated(EnumType.STRING)
    OrderStatus orderStatus;

    @Enumerated(EnumType.STRING)
    WayToGet wayToGet;

    @ManyToMany(cascade = CascadeType.REFRESH)
    @JoinColumns({
            @JoinColumn(name = "order_id"),
            @JoinColumn(name = "dish_id")
    })
    List<Dish> dishes;

    @OneToOne(cascade = CascadeType.REFRESH)
    Person client;

    @OneToOne(cascade = CascadeType.REFRESH)
    Address address;
}
