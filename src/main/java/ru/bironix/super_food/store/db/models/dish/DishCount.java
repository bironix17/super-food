package ru.bironix.super_food.store.db.models.dish;

import lombok.*;
import ru.bironix.super_food.store.db.interfaces.GetTotalPrice;
import ru.bironix.super_food.store.db.models.person.Role;

import javax.persistence.*;

import static org.apache.commons.lang3.ObjectUtils.defaultIfNull;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class DishCount implements GetTotalPrice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    @OneToOne(cascade = CascadeType.REFRESH)
    Dish dish;

    Integer count;

    @Override
    public int getTotalPrice() {
        return dish.getTotalPrice() * count;
    }

    @PrePersist
    void prePersist() {
        count = defaultIfNull(count, 1);
    }
}
