package ru.bironix.super_food.db.dish.models;

import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Dish {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @NonNull
    Integer id;

    @NonNull
    @OneToOne(optional = false, cascade = CascadeType.ALL)
    PicturePaths picturePaths;

    @NonNull
    @Column(nullable = false)
    String name;

    @NonNull
    @Column(nullable = false)
    String composition;

    @NonNull
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    CategoryType category;

    @NonNull
    @Column(nullable = false)
    String description;

    String allergens;

    @Builder.Default
    @Column(columnDefinition = "integer default 0")
    Integer baseIndexPortion = 0;

    @NonNull
    @OneToMany()
    List<Portion> portions;

    @OneToMany()
    List<Addon> addons;

    @OneToMany()
    List<Dish> dishes;
}