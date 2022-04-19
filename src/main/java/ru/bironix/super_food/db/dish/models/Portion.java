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

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    Integer id;

    String size;

    @NonNull
    @OneToOne(optional = false, cascade = CascadeType.ALL)
    @JoinColumn(name = "priceNowId", nullable = false)
    Price priceNow;

    @OneToOne(optional = true, cascade = CascadeType.ALL) //TODO fix
    @JoinColumn(name = "oldPriceId", nullable = true)
    Price oldPrice;
}
