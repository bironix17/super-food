package ru.bironix.super_food.db.dish.models;

import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Portion {

    @NonNull
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    String size;

    @NonNull
    @OneToOne(optional = false)
    @JoinColumn(name = "priceNowId", nullable = false)
    Price priceNow;

    @OneToOne(optional = true)
    @JoinColumn(name = "oldPriceId", nullable = true)
    Price oldPrice;
}
