package ru.bironix.super_food.db.generalModels;

import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class PicturePaths {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    Integer id;

    String small;

    String medium;

    @NonNull
    @Column(nullable = false)
    String large;
}