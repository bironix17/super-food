package ru.bironix.super_food.store.db.models.order;

import lombok.*;
import ru.bironix.super_food.store.db.models.dish.OrderedDish;
import ru.bironix.super_food.store.db.models.person.Address;
import ru.bironix.super_food.store.db.models.person.Person;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

import static org.apache.commons.lang3.ObjectUtils.defaultIfNull;

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

    @Column(nullable = false)
    LocalDateTime deliveryTime;

    @Column(nullable = false)
    Integer totalPrice;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    OrderStatus status;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    WayToGet wayToGet;

    @Column(length=500)
    String comment;

    @Enumerated(EnumType.STRING)
    PaymentMethod paymentMethod;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinColumns({
            @JoinColumn(name = "order_id"),
            @JoinColumn(name = "dishCount_id")
    })
    List<OrderedDish> dishes;

    @OneToOne(cascade = CascadeType.REFRESH, optional = false)
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
        this.comment = other.comment;
        this.paymentMethod = other.paymentMethod;
        this.dishes = other.dishes;
        this.client = other.client;
        this.address = other.address;
    }

    @PrePersist
    void prePersist() {
        created = defaultIfNull(created, LocalDateTime.now());
        status = defaultIfNull(status, OrderStatus.EXPECTS);
        wayToGet = defaultIfNull(wayToGet,  WayToGet.PICKUP);
    }
}
