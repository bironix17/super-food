package ru.bironix.super_food.db.models.dish;

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

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    Integer id;

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