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
public class Addon {

    //@NonNull  TODO
    @Builder.Default
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id = 0;

    @NonNull
    @Column(nullable = false)
    String name;

    @NonNull
    @Column(nullable = false)
    String picturePath;

    @NonNull
    @OneToOne(optional = false, cascade = CascadeType.ALL)
    @JoinColumn(name = "priceId", nullable = false)
    Price price;
}