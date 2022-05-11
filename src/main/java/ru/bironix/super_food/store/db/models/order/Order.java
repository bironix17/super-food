package ru.bironix.super_food.store.db.models.order;

import lombok.*;
import ru.bironix.super_food.store.db.models.dish.DishCount;
import ru.bironix.super_food.store.db.models.person.Address;
import ru.bironix.super_food.store.db.models.person.Person;

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

    @Column(nullable = false)
    LocalDateTime created;
    LocalDateTime deliveryTime;

    int totalPrice;

    @Builder.Default
    @Enumerated(EnumType.STRING)
    OrderStatus status = OrderStatus.EXPECTS;

    @Builder.Default
    @Enumerated(EnumType.STRING)
    WayToGet wayToGet = WayToGet.PICKUP;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinColumns({
            @JoinColumn(name = "order_id"),
            @JoinColumn(name = "dishCount_id")
    })
    List<DishCount> dishes;

    @OneToOne(cascade = CascadeType.REFRESH)
    Person client;

    @OneToOne(cascade = CascadeType.REFRESH)
    Address address;

    public Order(Order other) {
        this.id = other.id;
        this.created = other.created;
        this.deliveryTime = other.deliveryTime;
        this.totalPrice = other.totalPrice;
        this.status = other.status;
        this.wayToGet = other.wayToGet;
        this.dishes = other.dishes;
        this.client = other.client;
        this.address = other.address;
    }
}
