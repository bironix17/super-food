package ru.bironix.super_food.store.db.models.dish;

import lombok.*;
import ru.bironix.super_food.store.db.interfaces.GetTotalPrice;

import javax.persistence.*;

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

    @Builder.Default
    Integer count = 1;

    @Override
    public int getTotalPrice() {
        return dish.getTotalPrice() * count;
    }
}
